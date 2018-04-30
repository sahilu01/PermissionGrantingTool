package com.example.PermissionGrantingTool.Service.impl;

import com.example.PermissionGrantingTool.DTO.FileRequestDto;
import com.example.PermissionGrantingTool.DTO.PermissionGrantDto;
import com.example.PermissionGrantingTool.DTO.PermissionResponseDto;
import com.example.PermissionGrantingTool.Entity.PGT;
import com.example.PermissionGrantingTool.Entity.PgtFile;
import com.example.PermissionGrantingTool.Repository.PGTFileRepository;
import com.example.PermissionGrantingTool.Repository.PGTRepository;
import com.example.PermissionGrantingTool.Service.PGTService;
import static java.lang.Math.abs;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class PGTServiceImpl implements PGTService {
    @Autowired
    PGTRepository pgtRepository;
    @Autowired
    PGTFileRepository pgtFileRepository;
    @Autowired
    JavaMailSender sender;

    public static int approvers;
    public int last = 1;
    public static List<String> approverList = new ArrayList<>();
    public static List<PermissionResponseDto> permission = new ArrayList<>();
    public static FileRequestDto fileRequestDto1 = new FileRequestDto();
    public static List<String> reasonList = new ArrayList<>();
    public static Map<String,String> approverMap= new HashMap<>();
    public static final String URL = "http://192.168.225.172:8080/";

    @Override
    public List<PGT> getAllEmployees() {
        return pgtRepository.findAll();
    }

    @Override public List<String> isGranted(FileRequestDto fileRequestDto)
        throws InterruptedException, MessagingException {

      permission.clear();
      approverList.clear();
      reasonList.clear();
      approverMap.clear();
      String fileName = fileRequestDto.getFileName();
      fileRequestDto1 = fileRequestDto;

      PgtFile pgtFile = new PgtFile();
      pgtFile = pgtFileRepository.findByFileName(fileName);
      PGT employee = pgtRepository.findOne(fileRequestDto.getUserId());
      approvers = 0;
      if(employee.getLabel().equalsIgnoreCase("director"))
      {
        approvers = 1;
      }
      else {
        approvers = Integer.parseInt(pgtFile.getSensitivity());
      }
      List<PGT> pgtList = pgtRepository.findAll();
      List<String> empIdList = new ArrayList<>();



      for (PGT pgt : pgtList) {
        if (Integer.parseInt(pgt.getPriority()) >= Integer.parseInt(employee.getPriority()) && !(pgt
            .getEmpId().equalsIgnoreCase(employee.getEmpId())))
          empIdList.add(pgt.getEmpId());
      }

      Set<String> uniqueApprovers = new HashSet<>();
      int prevRes = 0;
      while(uniqueApprovers.size() < approvers) {
        Thread.sleep(100);
        updateLast(empIdList.size());
        int res = RandomNumberGen(empIdList.size());
        uniqueApprovers.add(empIdList.get(res));
      }
      approverList.addAll(uniqueApprovers);
      Iterable<PGT> approverDetailList =  pgtRepository.findAll(approverList);
      for(PGT pgt : approverDetailList){
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setTo(pgt.getEmail());
        helper.setText(
            "Hi! "+pgt.getName()+"( " + pgt.getEmpId() +" )"+ System.lineSeparator()+"You have a new file request to approve for the file named: "
                + fileName +System.lineSeparator()+
                System.lineSeparator()+
                "FROM:"+System.lineSeparator()+
                employee.getName()+System.lineSeparator()+
                employee.getLabel()+System.lineSeparator()+
                System.lineSeparator()+
                "check the following url to approve :"+System.lineSeparator()
                + URL+"RequestUI-1/LoginPage.html" +System.lineSeparator() + "Thanks!");
        helper.setSubject("File Approval");
        sender.send(message);
      }
      return approverList;

    }


    @Override
    public String grantPermission(PermissionGrantDto permissionGrantDto) {
      PermissionResponseDto permissionResponseDto = new PermissionResponseDto();
      int flag = 1;
      for (int i = 0; i < approverList.size(); i++) {
        if (permissionGrantDto.getEmpId().equals(approverList.get(i))) {
          flag = 0;
        }
      }

      if (flag == 1) {
        return "You are not allowed to give permission";
      } else {

        PGT pgt = new PGT();
        pgt = pgtRepository.findOne(permissionGrantDto.getEmpId());

        permissionResponseDto.setLabel(pgt.getLabel());
        permissionResponseDto.setAccept(permissionGrantDto.getAccept());
        permissionResponseDto.setEmpId(permissionGrantDto.getEmpId());

        if(approverMap.get(permissionGrantDto.getEmpId()) == null) {
          permission.add(permissionResponseDto);
          approverMap.put(permissionGrantDto.getEmpId(),"done");
          if(permissionGrantDto.getReason()!=null) {
            reasonList.add(permissionGrantDto.getReason());
          }
          return "Permission recorded";
        }
        else {

          return "Permissions already given for the request";
        }

      }
    }

    @Override
    public List<PermissionResponseDto> getPermissionStatus() {
        return permission;
    }


    @Override
    public String getFile() throws IOException {

        int i = 0;
        int flag = 0;

        File dir = new File("/Users/coviam/Downloads/apache-tomcat-8.5.24/webapps/RequestUI-1");
        dir.mkdirs();
        File file = new File(dir, fileRequestDto1.getFileName());

        BufferedWriter br = null;
        if (!file.exists()) {
            file.createNewFile();
        }
        PgtFile pgtFile = new PgtFile();
        pgtFile = pgtFileRepository.findByFileName(fileRequestDto1.getFileName());

        for (PermissionResponseDto permissionResponseDto : permission) {
            while (i != permission.size()) {

                if (permission.get(i).getAccept().equalsIgnoreCase("no")) {
                  return "Access Denied";
                }
                i++;
            }
        }


        try {
            br = new BufferedWriter(new FileWriter(file));
            br.write(pgtFile.getContent());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            br.close();

        }

        permission.clear();
        return "http://192.168.225.172:8080/RequestUI-1/"+file.getName();




    }

    @Override
    public boolean isTested(FileRequestDto fileRequestDto) {
        return true;
    }

  @Override public List<String> getReasons() {
    return reasonList;
  }
@Override
  public Boolean validateUser(String userId){
      if(pgtRepository.findOne(userId)!=null){
        return true;
      }
      else{
        return false;
      }
  }
  public void updateLast (int max){
            last = (int) (System.currentTimeMillis());
        }

        public int RandomNumberGen ( int max){

            last = (last * 32719  + 3) % 32749;
            int res = abs(last % max);
            return res;

        }

}
