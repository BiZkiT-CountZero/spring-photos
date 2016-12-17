package com.raveleen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by Святослав on 16.12.2016.
 */
@Controller
public class MainController {
    @Autowired
    private PhotoDAO photoDAO;

    @Autowired
    private Archive archive;

    @RequestMapping(value = "/zip", method = RequestMethod.GET, produces = "application/zip")
    public ResponseEntity<byte[]> zipFiles() {
        byte[] zip = null;

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ZipOutputStream zipOutputStream = new ZipOutputStream(baos);

            for (Map.Entry<String, byte[]> a : archive.getMap().entrySet()) {
                ZipEntry zp = new ZipEntry("" + a.getValue());
                zp.setSize(a.getValue().length);
                zipOutputStream.putNextEntry(zp);
                zipOutputStream.write(a.getValue());
            }

            zipOutputStream.closeEntry();
            zipOutputStream.close();

            archive.refreshArchive();
            zip = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Arch sent.");
            return ResponseEntity
                    .ok()
                    .contentLength(zip.length)
                    .body(zip);
        }
    }

    @RequestMapping("/")
    public ModelAndView listDishes() {
        return new ModelAndView("main", "photos", photoDAO.getWholeList());
    }

    @RequestMapping("/zipper")
    public String getZipper(Model model) {
        return "zipper";
    }

    @RequestMapping("/add-form")
    public String getAddForm(Model model) {
        return "add-form";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ModelAndView delete(@RequestParam(value = "id") long[] id) {
        photoDAO.delete(id);
        return new ModelAndView("main", "photos", photoDAO.getWholeList());
    }

    @RequestMapping("/image/{file_id}")
    public void getFile(HttpServletRequest request, HttpServletResponse response, @PathVariable("file_id") long fileId) {
        try {
            byte[] content = photoDAO.getPhoto(fileId);
            response.setContentType("image/png");
            response.getOutputStream().write(content);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView addPhoto(@RequestParam(value = "photo") MultipartFile body,
                                 HttpServletRequest request,
                                 HttpServletResponse response) {
        try {
            Photo photo = new Photo(body.getOriginalFilename(), body.getBytes());
            photoDAO.addPhoto(photo);
            return new ModelAndView("main", "photos", photoDAO.getWholeList());
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        }
    }

    @RequestMapping(value = "/add-file", method = RequestMethod.POST)
    public ModelAndView addFile(@RequestParam(value = "file") MultipartFile body,
                                 HttpServletRequest request,
                                 HttpServletResponse response) {
        try {
            archive.addFile(body.getOriginalFilename(), body.getBytes());
            System.out.println("File " + body.getOriginalFilename() + " added.");
            return new ModelAndView("/zipper", "files", archive.getMap().keySet());
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        }
    }
}
