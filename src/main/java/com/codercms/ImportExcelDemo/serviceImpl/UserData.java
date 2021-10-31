package com.codercms.ImportExcelDemo.serviceImpl;

import com.codercms.ImportExcelDemo.Entities.UserEntity;
import com.codercms.ImportExcelDemo.Exceptions.UserException;
import com.codercms.ImportExcelDemo.Models.User;
import com.codercms.ImportExcelDemo.Repositories.UserRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class UserData {

    @Autowired
    UserRepository userRepository;
    public static HashMap<String, List<String>> hm = new HashMap<>();
    public static ArrayList<String> records = new ArrayList<>();

    /**
     *  Method to get Cell Value in String Format
     * @param row
     * @param cellNo
     * @return
     */
    private String getCellValue(Row row, int cellNo) {
        DataFormatter formatter = new DataFormatter();

        Cell cell = row.getCell(cellNo);

        return formatter.formatCellValue(cell);
    }

    private int convertStringToInt(String str) {
        int result = 0;

        if (str == null || str.isEmpty() || str.trim().isEmpty()) {
            return result;
        }

        result = Integer.parseInt(str);

        return result;
    }

    /**
     *  Method to validate Database Conditions
     * @param value
     * @return
     */
    public static boolean isValidValue(String value) {
        return value.length() > 255 ? true: false;
    }
    public  static List<String> isValidate(User user)
    {
        List<String> list = new ArrayList<>();

        try{
            if(isValidValue(user.getUsername())) {

                throw new UserException("Username is not valid");
            }
        }catch(UserException e) {
            System.out.println(e);
            list.add(e.getMessage());
        }
        try{
            if(isValidValue(user.getEmail())) {

                throw new UserException("Email is not valid");
            }
        }catch(UserException e) {
            list.add(e.getMessage());
        }
        try{
            if(isValidValue(user.getContact())) {

                throw new UserException("Contact is not valid");
            }
        }catch(UserException e) {
            list.add(e.getMessage());
        }
        return list;
    }

    /**
     * Method to get Data from Excel and Store it in List of Users.
     * @param userFile
     * @return
     * @throws IOException
     */
    public List<User> getDataFromExcel(MultipartFile userFile) throws IOException {

        List<User> users = new ArrayList<>();
        XSSFWorkbook workbook = new XSSFWorkbook(userFile.getInputStream());
        // Read user data form excel file sheet.
        XSSFSheet worksheet = workbook.getSheetAt(0);
        Row headings = worksheet.getRow(0);
        Cell cellHeadings = headings.getCell(0);
        int lastCellNumber = headings.getLastCellNum();
        for (int index = 0; index < worksheet.getPhysicalNumberOfRows(); index++) {
            if (index > 0) {
                XSSFRow row = worksheet.getRow(index);

                User user = new User();


                user.firstname = getCellValue(row, 0);
                user.email = getCellValue(row, 1);
                user.password = String.valueOf(UUID.randomUUID());
                user.contact = getCellValue(row, 2);
                user.username = getCellValue(row, 3);

                if (isValidate(user).isEmpty()) {
                    if (user.username != "" && user.email != "" && user.contact != "" && user.firstname != "") {
                        users.add(user);
                    }
                } else {
                    List<String> exceptions = isValidate(user);
                    hm.put(user.username, exceptions);
                }
            }
        }
        return users;
    }


    /**
     * Method to Store List of Users into DataBase and checking Database is already updated or not.
     * @param users
     */
    public boolean storeInDb(List<User> users){
    // take data from db and check values
    List<UserEntity> dbEntities = userRepository.findAll();
    boolean updated = false;


        for (int i = 0; i < dbEntities.size(); i++) {
            updated = false;

            if (dbEntities.get(i).username.equals(users.get(i).username)) {
                updated = true;
                records.add(dbEntities.get(i).username);
            }
        }



    if (!updated) {
        // Save to db.
        List<UserEntity> entities = new ArrayList<>();
        if (users.size() > 0) {
            users.forEach(x -> {
                UserEntity entity = new UserEntity();

                entity.firstname = x.firstname;
                entity.email = x.email;
                entity.contact = x.contact;
                entity.password = x.password;
                entity.username = x.username;
                entities.add(entity);
            });
            userRepository.saveAll(entities);
        }
    }
  return updated;
}

public String getPasswordsByUsername(String username)
{
    try {
        return userRepository.findByUsername(username);
    }catch(Exception e)
    {
        throw new UserException("Username not found");
    }
}


}
