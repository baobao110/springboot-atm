package com.dyuanit.atm.springbootatm.controller;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.OutputStream;
import java.util.List;

@Controller
public class UploadController extends BaseController {

    @RequestMapping("/upload2")
    public void upload2(String username, MultipartFile myFile, HttpServletRequest request, HttpServletResponse response) {


        System.out.println("username=" + username);

        try {
            System.out.println(myFile.getBytes().length);
            String filePath = WebUtils.getRealPath(request.getServletContext(), "/upload/" + getUserId(request.getSession()));
            System.out.println(filePath);
            myFile.transferTo(new File(filePath));
//            FileUtils.writeByteArrayToFile(new File(filePath), myFile.getBytes());

        } catch (Exception e) {
            e.printStackTrace();
        }

        try (OutputStream os = response.getOutputStream()) {
            String srcprit = "<script>parent.reloadAvatar();</script>";
            os.write(srcprit.getBytes());
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/upload")
    public void upload(HttpServletRequest request) {

        try {

            boolean isMultipart = ServletFileUpload.isMultipartContent(request);
            System.out.println(isMultipart);
            String tmpPath = WebUtils.getRealPath(request.getServletContext(), "/tmp");
            String filePath = WebUtils.getRealPath(request.getServletContext(), "/upload");

            DiskFileItemFactory factory = new DiskFileItemFactory();

            factory.setSizeThreshold(1024);
            factory.setRepository(new File(tmpPath));

            ServletFileUpload upload = new ServletFileUpload(factory);

            upload.setSizeMax(1024 * 150);

            List<FileItem> items = upload.parseRequest(request);

            for (FileItem item : items) {

                if (item.isFormField()) {
                    String name = item.getFieldName();
                    String value = item.getString();
                    System.out.println("name=" + name + ", value=" + value );
                } else {
                    String fieldName = item.getFieldName();
                    String filename = item.getName();
                    String contentType = item.getContentType();
                    long sizeInBytes = item.getSize();

                    //保存文件
                    File saveFile = new File(filePath + "/" + filename);
                    item.write(saveFile);

                    System.out.println("file info[" + fieldName + "," + filename + "," + contentType + "," + sizeInBytes + "]");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
