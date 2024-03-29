package com.advance.mistra.utils.system;

import java.io.File;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;
import java.util.ArrayList;
import java.util.List;

import com.sun.management.OperatingSystemMXBean;

/**
 * @author Mistra
 * @ Version: 1.0
 * @ Time: 2020/5/25 21:31
 * @ Description:
 * @ Copyright (c) Mistra,All Rights Reserved.
 * @ Github: https://github.com/MistraR
 * @ CSDN: https://blog.csdn.net/axela30w
 */
public class SystemInfoUtil {

    private static final int CPU_TIME = 500;
    private static final int PERCENT = 100;
    private static final int FAULT_LENGTH = 10;

    /**
     * 获取内存使用率
     */
    public static String getMemory() {
        OperatingSystemMXBean o = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
        // 剩余的物理内存
        long totalVirtualMemory = o.getTotalSwapSpaceSize();
        long freePhysicalMemorySize = o.getFreePhysicalMemorySize();
        double compare = (1 - freePhysicalMemorySize * 1.0 / totalVirtualMemory) * 100;
        return (int) compare + "%";
    }

    /**
     * 获取文件系统使用率
     *
     * @return List<String>
     */
    public static List<String> getDisk() {
        // 操作系统
        List<String> list = new ArrayList<String>();
        for (char c = 'A'; c <= 'Z'; c++) {
            String dirName = c + ":/";
            File win = new File(dirName);
            if (win.exists()) {
                long total = (long) win.getTotalSpace();
                long free = (long) win.getFreeSpace();
                double compare = (Double) (1 - free * 1.0 / total) * 100;
                String str = c + ":盘已使用" + (int) compare + "%";
                list.add(str);
            }
        }
        return list;
    }

    /**
     * 获得cpu使用率
     *
     * @return String
     */
    public static String getCpuRatioForWindows() {
        try {
            String procCmd = System.getenv("windir")
                    + "\\system32\\wbem\\wmic.exe process get Caption,CommandLine,KernelModeTime,ReadOperationCount,ThreadCount,UserModeTime,WriteOperationCount";
            // 取进程信息
            long[] c0 = readCpu(Runtime.getRuntime().exec(procCmd));
            Thread.sleep(CPU_TIME);
            long[] c1 = readCpu(Runtime.getRuntime().exec(procCmd));

            if (c0 != null && c1 != null) {
                long idletime = c1[0] - c0[0];
                long busytime = c1[1] - c0[1];
                return Double.valueOf(PERCENT * (busytime) * 1.0 / (busytime + idletime)).intValue() + "%";
            } else {
                return 0 + "%";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0 + "%";
        }
    }

    private static long[] readCpu(final Process proc) {
        long[] retn = new long[2];
        try {
            proc.getOutputStream().close();
            InputStreamReader ir = new InputStreamReader(proc.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            String line = input.readLine();
            if (line == null || line.length() < FAULT_LENGTH) {
                return null;
            }
            int capidx = line.indexOf("Caption");
            int cmdidx = line.indexOf("CommandLine");
            int rocidx = line.indexOf("ReadOperationCount");
            int umtidx = line.indexOf("UserModeTime");
            int kmtidx = line.indexOf("KernelModeTime");
            int wocidx = line.indexOf("WriteOperationCount");
            long idletime = 0;
            long kneltime = 0;
            long usertime = 0;
            while ((line = input.readLine()) != null) {
                if (line.length() < wocidx) {
                    continue;
                }
                // 字段出现顺序：Caption,CommandLine,KernelModeTime,ReadOperationCount,
                //ThreadCount,UserModeTime,WriteOperation
                String caption = Bytes.substring(line, capidx, cmdidx - 1).trim();
                String cmd = Bytes.substring(line, cmdidx, kmtidx - 1).trim();
                if (cmd.contains("wmic.exe")) {
                    continue;
                }
                String s1 = Bytes.substring(line, kmtidx, rocidx - 1).trim();
                String s2 = Bytes.substring(line, umtidx, wocidx - 1).trim();
                if ("System Idle Process".equals(caption) || "System".equals(caption)) {
                    if (s1.length() > 0) {
                        idletime += Long.parseLong(s1);
                    }
                    if (s2.length() > 0) {
                        idletime += Long.parseLong(s2);
                    }
                    continue;
                }
                if (s1.length() > 0) {
                    kneltime += Long.parseLong(s1);
                }
                if (s2.length() > 0) {
                    usertime += Long.parseLong(s2);
                }
            }
            retn[0] = idletime;
            retn[1] = kneltime + usertime;
            return retn;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                proc.getInputStream().close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    static class Bytes {
        public static String substring(String src, int start_idx, int end_idx) {
            byte[] b = src.getBytes();
            String tgt = "";
            for (int i = start_idx; i <= end_idx; i++) {
                tgt += (char) b[i];
            }
            return tgt;
        }
    }

    public static void testString(){
        String str = new String("abcdedf");
        int count = 0;
        System.out.println("当前JVM最大内存："+Runtime.getRuntime().maxMemory()/1024/1024+"m--"+Runtime.getRuntime().maxMemory()+"byte");
        System.out.println("当前JVM已用内存："+Runtime.getRuntime().totalMemory()/1024/1024+"m--"+Runtime.getRuntime().totalMemory()+"byte");
        MemoryUsage mu = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
        long getCommitted = mu.getCommitted();
        long getInit = mu.getInit();
        long getUsed = mu.getUsed();
        long max = mu.getMax();
        System.out.println(mu.toString());
    }

    public static void main(String[] args) throws Exception {
        testString();
        System.out.println("cpu占有率=" + SystemInfoUtil.getCpuRatioForWindows());
        System.out.println("可使用内存=" + SystemInfoUtil.getMemory());
        System.out.println("各盘占用情况：" + SystemInfoUtil.getDisk());
    }
}
