package cn.dozyx.core.utli.executor

import java.util.concurrent.ExecutorService
import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 * Create by timon on 2019/5/17
 **/
fun get(): ExecutorService = ThreadPoolExecutor(1, 1, 0, TimeUnit.MICROSECONDS, LinkedBlockingDeque<Runnable>())