package com.shawn.web.controller;

import cn.hutool.core.util.ReferenceUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.TypeUtil;
import com.shawn.constant.PageConstant;
import com.shawn.constant.ResourceNameConstant;
import com.shawn.model.dto.PaginatedResult;
import com.shawn.model.entity.Book;
import com.shawn.service.BookInterface;
import com.shawn.service.ISubmitTaskInterface;
import com.shawn.util.PageUtil;
import com.shawn.util.SpringContextUtils;
import com.shawn.web.exception.ResourceNotFoundException;
import io.swagger.annotations.ApiOperation;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * @author Xiaoyue Xiao
 */
@RestController
@RequestMapping("/books")
public class BookController {

    private BookInterface bookService;
    @Resource
    private ResourceLoader resourceLoader;

    @Autowired
    public BookController(BookInterface bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<?> getBooks(@RequestParam(value = "page", required = false) String pageString,
                                      @RequestParam(value = "per_page", required = false) String perPageString) {
        // Parse request parameters
        int page = PageUtil.parsePage(pageString, PageConstant.PAGE);
        int perPage = PageUtil.parsePerPage(perPageString, PageConstant.PER_PAGE);
        PaginatedResult paResult = new PaginatedResult();
        paResult.setData(bookService.getBooksByPage(page, perPage));
        paResult.setTotalPage(bookService.getTotalPage(perPage));
        return ResponseEntity.ok(paResult);
    }

    @PostMapping("/saveBook")
    public void saveBook(Book book) {
        bookService.saveBook(book);
    }

    @PostMapping("/submitTask")
    public void submitTask(Book book) throws IllegalAccessException, InstantiationException {
        Object service = SpringContextUtils.getBean("bookService");
        ResolvableType resolvableType = ResolvableType.forClass(service.getClass());
        System.out.println(resolvableType.getRawClass().getName());
        Type superCls = service.getClass().getGenericSuperclass();
        ISubmitTaskInterface bookService = (ISubmitTaskInterface) service;
        Method submitMethod = ReflectUtil.getMethodByName(service.getClass(), "submitTask");
        // bookService.getClass().getMethod()
        Class paramCls = TypeUtil.getFirstParamClass(submitMethod);
        Book param = (Book) paramCls.newInstance();
        System.out.println(param.getAuthor());
        bookService.submitTask(book);
//		bookService.submitTask(book);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<?> getBookById(@PathVariable Long bookId) {
        ResourceNotFoundException reException = new ResourceNotFoundException();
        Optional<Book> book = bookService.getBookById(bookId);
        reException.setResourceName(ResourceNameConstant.BOOK);
        reException.setId(bookId);
        if (book.isPresent()) {
            return ResponseEntity.ok(book);
        } else {
            throw reException;
        }
    }

    @PostMapping
    public ResponseEntity<?> postBook(@RequestBody Book book) {
        bookService.saveBook(book);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(book.getId())
                .toUri();

        return ResponseEntity.created(location).body(book);

    }

    @PutMapping("/{bookId}")
    public ResponseEntity<?> putBook(@PathVariable Long bookId, @RequestBody Book book) {
        assertBookExist(bookId);
        book.setId(bookId);
        bookService.modifyBookOnNameById(book);

        return ResponseEntity.status(HttpStatus.OK).body(book);
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<?> deleteBook(@PathVariable Long bookId) {
        assertBookExist(bookId);

        bookService.deleteBookById(bookId);

        return ResponseEntity.noContent().build();
    }

    /**********************************
     * HELPER METHOD
     **********************************/
    private void assertBookExist(Long bookId) {
        ResourceNotFoundException reException = new ResourceNotFoundException();
        reException.setResourceName(ResourceNameConstant.BOOK);
        reException.setId(bookId);
        Optional<Book> book = bookService.getBookById(bookId);
        if (!book.isPresent()) {
            throw reException;
        }
    }

    /**
     * <p>Discription:[下载模板功能]</p>
     * Created on 2018年2月1日 上午11:57:59
     *
     * @param response response对象
     * @param request  response对象
     * @author:[全冉]
     */
    @ApiOperation("下载模板功能")
    @GetMapping("/downloadTemplate")
    public void downloadTemplate(HttpServletResponse response, HttpServletRequest request) {
        InputStream inputStream = null;
        ServletOutputStream servletOutputStream = null;
        try {
            String filename = "外包人员信息导入模板.xlsx";
            String path = "template/outsourceUserInfo.xlsx";
            org.springframework.core.io.Resource resource = resourceLoader.getResource("classpath:" + path);
            response.setContentType("application/vnd.ms-excel");
            response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.addHeader("charset", "utf-8");
            response.addHeader("Pragma", "no-cache");
            String encodeName = URLEncoder.encode(filename, StandardCharsets.UTF_8.toString());
            response.setHeader("Content-Disposition", "attachment; filename=" + encodeName);
            servletOutputStream = response.getOutputStream();
            inputStream = resource.getInputStream();
            IOUtils.copy(inputStream, servletOutputStream);
            response.flushBuffer();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (servletOutputStream != null) {
                    servletOutputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
