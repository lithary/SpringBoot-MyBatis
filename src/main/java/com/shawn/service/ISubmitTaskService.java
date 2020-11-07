package com.shawn.service;

public interface ISubmitTaskService {
    /**
     * 提交申请
     * @param in
     * @param <T>
     */
    <T> void submitTask(T in);

    /**
     * 提交申请之后执行的方法
     * @param in
     * @param <T>
     */
    <T> void afterSubmit(T in);

    /**
     * 审核通过
     * @param in
     * @param <T>
     */
    <T> void approveTask(T in);

    /**
     * 审核通过之后执行的方法
     * @param in
     * @param <T>
     */
    <T> void afterApprove(T in);

    /**
     * 驳回
     * @param in
     * @param <T>
     */
    <T> void rejectTask(T in);

    /**
     * 驳回之后执行的方法
     * @param in
     * @param <T>
     */
    <T> void afterReject(T in);
}
