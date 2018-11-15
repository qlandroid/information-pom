package com.information.base;

import java.util.ArrayList;
import java.util.List;

public class Result {

    private int code;

    private List<Object> list;
    private Object data;
    private String message;

    private Long total;
    private Integer pageSizes;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getList() {
        return list;
    }

    public void setList(List<Object> list) {
        this.list = list;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getPageSizes() {
        return pageSizes;
    }

    public void setPageSizes(Integer pageSizes) {
        this.pageSizes = pageSizes;
    }

    public static Result ok() {
        Result r = new Result();
        r.setCode(200);
        return r;
    }

    public static Result ok(Object data) {
        Result r = new Result();
        r.setCode(200);
        r.setData(data);
        return r;
    }

    public static Result okList(List l) {
        Result r = new Result();
        r.setCode(200);
        if (l == null) {
            l = new ArrayList();
        }
        r.setList(l);
        return r;
    }

    public static Result okList(List l, Long total, int pages) {
        Result r = new Result();
        r.setCode(200);
        if (l == null) {
            l = new ArrayList();
        }
        r.setTotal(total);
        r.setPageSizes(pages);
        r.setList(l);
        return r;
    }

    public static Result error(int errorCode, String msg) {
        Result r = new Result();
        r.setCode(errorCode);
        r.setMessage(msg);
        return r;
    }

    public static Result error(String msg) {
        Result r = new Result();
        r.setCode(101);
        r.setMessage(msg);
        return r;
    }
}
