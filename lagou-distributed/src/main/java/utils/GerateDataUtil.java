package utils;

import java.util.List;
import java.util.Random;

public class GerateDataUtil {

    public static void main(String[] args) {

        int dataCount = 1000;

        List<String> familyNames = List.of("张", "李", "王", "赵", "刘", "陈", "杨", "黄", "周", "吴");
        List<String> names = List.of("一", "二", "三", "四", "五", "六", "七", "八", "九", "十");
        List<String> educations = List.of("bachelor","master","doctor");
        List<String> addresses = List.of("北京", "上海", "广州", "深圳", "杭州", "成都", "西安", "南京", "武汉", "重庆");
        List<String> sexs = List.of("男", "女");
        List<String> phones = List.of("130012300000", "131003450000", "132034600000", "133006780000", "13400772300");
        Random random = new Random();
        for (int i = 0; i < dataCount; i++) {
            String familyName = familyNames.get(random.nextInt(familyNames.size()));
            String lastName = names.get(random.nextInt(names.size()));
            String name = familyName + lastName;
            String state = "未归档";
            String education = educations.get(random.nextInt(educations.size()));
            String address = addresses.get(random.nextInt(addresses.size()));
            String sex = sexs.get(random.nextInt(sexs.size()));
            String sql = "insert into resume(name, state, education, address, sex, phone) values(?, ?, ?, ?, ?, ?)";
            String phone = phones.get(random.nextInt(phones.size()));
            JdbcUtil.executeUpdate(sql, name, state, education, address, sex, phone);
        }
    }

}
