package com.shawn.service;

public interface ISubmitTaskInterface<T> {
    /**
     * 提交申请
     *
     * @param in
     */
    void submitTask(T in);

    /**
     * 提交申请之后执行的方法
     *
     * @param in
     */
    default void afterSubmit(T in) {

    }

    /**
     * 审核通过
     *
     * @param in
     */
    default void approveTask(T in) {

    }

    /**
     * 审核通过之后执行的方法
     *
     * @param in
     */
    default void afterApprove(T in) {

    }

    /**
     * 驳回
     *
     * @param in
     */
    default void rejectTask(T in) {

    }

    /**
     * 驳回之后执行的方法
     *
     * @param in
     */
    default void afterReject(T in) {

    }
}
