package com.example.PermissionGrantingTool.Service;

import com.example.PermissionGrantingTool.DTO.FileRequestDto;
import com.example.PermissionGrantingTool.DTO.PermissionGrantDto;
import com.example.PermissionGrantingTool.DTO.PermissionResponseDto;
import com.example.PermissionGrantingTool.Entity.PGT;

import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.util.List;


public interface PGTService {

    public List<PGT> getAllEmployees();

    List<String> isGranted(FileRequestDto fileRequestDto)
        throws InterruptedException, MessagingException;

    String grantPermission(PermissionGrantDto permissionGrantDto);

    List<PermissionResponseDto> getPermissionStatus();

    String getFile() throws IOException;

    boolean isTested(FileRequestDto fileRequestDto);

    List<String> getReasons();

    Boolean validateUser(String userId);
}
