package com.sirius.tree;

import com.sirius.tree.domain.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 数据查询性能测试
 * 演示使用Stream API进行大数据量的并行查询
 */
public class App {

    private static List<UserInfoCathe> userInfoList = new ArrayList<>();
    public static List<UserTypeCathe> userTypeList = new ArrayList<>();
    public static List<UserCodeCathe> userCodeList = new ArrayList<>();

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private static Random random = new Random();

    public static void main(String[] args) throws ParseException {
        System.out.println("开始生成");
        LocalDateTime localDate1 = LocalDateTime.now();
        for (int i = 0; i < 200000; i++) {
            userInfoList.add(new UserInfoCathe(i));
            userInfoList.add(new UserInfoCathe(i));
            userInfoList.add(new UserInfoCathe(i));
            int typeCount = random.nextInt(10);
            for (int j = 0; j < typeCount; j++) {
                userTypeList.add(new UserTypeCathe(i));
            }
            int codeCount = random.nextInt(20);
            for (int j = 0; j < codeCount; j++) {
                userCodeList.add(new UserCodeCathe(i));
            }
        }
        // 查询条件
        Search search = new Search();
        search.setStartDate(sdf.parse("2019-05-05"));
        search.setEndDate(sdf.parse("2019-06-23"));
        search.setSex("2");
        for (int i = 1; i < 10; i++) {
            int num = i * 10;
            search.getType().add("1" + num);
        }
        for (int i = 2; i < 20; i++) {
            int num = i * 5;
            search.getCode().add("2" + num);
        }
        // 开始查询
        LocalDateTime localDate2 = LocalDateTime.now();
        System.out.println("生成结束 耗时" + localDate1.until(localDate2, ChronoUnit.MILLIS) + "毫秒\n开始查询");
        List<UserInfo> userInfos = searchFunction(search);
        LocalDateTime localDate3 = LocalDateTime.now();
        System.out.println("查询结束 耗时" + localDate2.until(localDate3, ChronoUnit.MILLIS) + "毫秒 结果数为" + userInfos.size());
    }


    private static List<UserInfo> searchFunction(Search search) {
        Stream<UserInfoCathe> stream = userInfoList.parallelStream();
        // Date条件
        if (search.getStartDate() != null && search.getEndDate() != null) {
            stream = stream.filter(c -> c.getBirthday().getTime() >= search.getStartDate().getTime() && c.getBirthday().getTime() <= search.getEndDate().getTime());
        }
        // Sex条件
        if (search.getSex() != null) {
            stream = stream.filter(c -> c.getSex().equals(search.getSex()));
        }
        // Type条件
        if (search.getType().size() > 0) {
            List<Integer> ids = userTypeList.parallelStream().filter(c -> search.getType().contains(c.getType())).map(UserTypeCathe::getId).collect(Collectors.toList());
            stream = stream.filter(c -> ids.contains(c.getId()));
        }
        // Code条件
        if (search.getCode().size() > 0) {
            List<Integer> ids = userCodeList.parallelStream().filter(c -> search.getCode().contains(c.getCode())).map(UserCodeCathe::getId).collect(Collectors.toList());
            stream = stream.filter(c -> ids.contains(c.getId()));
        }
        return stream.distinct().map(UserInfo::new).collect(Collectors.toList());
    }
}
