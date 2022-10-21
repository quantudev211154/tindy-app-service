package com.tindy.app.service.impl;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.tindy.app.dto.request.UserRequest;
import com.tindy.app.dto.respone.UserRespone;
import com.tindy.app.mapper.MapData;
import com.tindy.app.model.entity.User;
import com.tindy.app.repository.UserRepository;
import com.tindy.app.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import static java.net.URLEncoder.encode;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private static final String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/tindy-app-service.appspot.com/o/%s?alt=media&token=%s";
    private final UserRepository userRepository;
    @Override
    public UserRespone getUserInfo(String phone) {
        return MapData.mapOne(userRepository.findByPhone(phone).orElseThrow(()->new UsernameNotFoundException("User is not found!")),UserRespone.class);
    }

    @Override
    public UserRespone uploadFile(MultipartFile multipartFile, Integer userId) {
        try {
            String fileName = multipartFile.getOriginalFilename();
//            log.info("upload image:  File Name : {}", multipartFile.getOriginalFilename());
            assert fileName != null;
            fileName = UUID.randomUUID().toString().concat(this.getExtension(fileName));
            File file = this.convertToFile(multipartFile,fileName);

            String url = this.uploadFile(file,fileName);

            file.delete();

            User user = userRepository.findById(userId).orElseThrow(()-> new UsernameNotFoundException("Not found user"));
            user.setAvatar(url);

            return MapData.mapOne(userRepository.save(user), UserRespone.class);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserRespone updateUser(UserRequest userRequest, Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new UsernameNotFoundException("Not found"));
        user.setUpdatedAt(new Date(System.currentTimeMillis()));
        log.info("HieuLog: "+userRequest.toString());

        if(userRequest.getEmail() != null){
            user.setEmail(userRequest.getEmail());
            log.info("HieuLog: "+userRequest.toString());

        }
        if(userRequest.getFullName() != null){
            user.setFullName(userRequest.getFullName());
            log.info(user.toString());

        }
        User userUpdated = userRepository.save(user);

        return MapData.mapOne(userUpdated, UserRespone.class);
    }


    private String uploadFile(File file, String fileName) throws IOException {
        BlobId blobId = BlobId.of("tindy-app-service.appspot.com", fileName);
        Map<String, String> map = new HashMap<>();
        map.put("firebaseStorageDownloadTokens", fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setMetadata(map).setContentType("media").build();
        Credentials credentials = GoogleCredentials
                .fromStream(Files.newInputStream(Paths.get("src/main/resources/credentials.json")));
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

        storage.create(blobInfo, Files.readAllBytes(file.toPath()));
        log.info(fileName);
        return String.format(DOWNLOAD_URL, encode(fileName, StandardCharsets.UTF_8.name()), encode(fileName, StandardCharsets.UTF_8.name()));
    }

    private File convertToFile(MultipartFile multipartFile, String fileName) throws IOException {
        File tempFile = new File(fileName);
//        log.info("upload image:  File Name : {}", multipartFile.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(multipartFile.getBytes());
        }
        return tempFile;
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }
}
