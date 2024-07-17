package org.sl.controller;


import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RequestMapping
public class FileController {

    /**
     * 上传
     * @return
     * @throws IOException
     */

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // 保存文件到服务器目录，‌例如resources/file目录下
            Path dest = Paths.get("/files", file.getOriginalFilename());
            Files.copy(file.getInputStream(), dest);
            return ResponseEntity.ok("文件上传成功");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("文件上传失败");
        }
    }


    /**
     * 下载
     * @param filename
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "download/{filename}")
    public void download(@PathVariable String filename, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //获取文件的绝对路径
        String realPath = request.getSession().getServletContext().getRealPath("upload");
        //获取输入流对象（用于读文件）
        FileInputStream fis = new FileInputStream(new File(realPath, filename));
        //获取文件后缀（.txt）
        String extendFileName = filename.substring(filename.lastIndexOf('.'));
        //动态设置响应类型，根据前台传递文件类型设置响应类型
        response.setContentType(request.getSession().getServletContext().getMimeType(extendFileName));
        //设置响应头,attachment表示以附件的形式下载，inline表示在线打开
        response.setHeader("content-disposition","attachment;fileName="+ URLEncoder.encode(filename,"UTF-8"));
        //获取输出流对象（用于写文件）
        ServletOutputStream os = response.getOutputStream();
        //下载文件,使用spring框架中的FileCopyUtils工具
        FileCopyUtils.copy(fis,os);

    }

    /**
     * 下载
     * @param filename
     * @return
     */
    @GetMapping("/download2/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
        try {
            // 获取文件路径和名称
            Path file = Paths.get("路径", filename);
            // 构建Resource对象，‌表示要下载的文件资源
            Resource resource = new UrlResource(file.toUri());
            // 设置HTTP响应头信息，‌如Content-Disposition等，‌以指示浏览器进行下载操作
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);
            // 返回构建好的ResponseEntity对象，‌包含文件内容和HTTP响应头信息
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(resource);
        } catch (Exception e) {
            // 处理异常，‌如文件不存在等错误情况
            return ResponseEntity.notFound().build();
        }
    }

}
