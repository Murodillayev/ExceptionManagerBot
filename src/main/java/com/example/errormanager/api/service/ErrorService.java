package com.example.errormanager.api.service;

import com.example.errormanager.api.domain.ErrorMessage;
import com.example.errormanager.bot.ErrorManagerBot;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Objects;


/**
 * @author Muhammadkomil Murodillayev, ср 10:58. 8/10/22
 */

@Service
public class ErrorService {

    private final ErrorManagerBot bot;

    private final DeveloperService developerService;

    public ErrorService(ErrorManagerBot bot, DeveloperService developerService) {
        this.bot = bot;
        this.developerService = developerService;
    }

    public void send(ErrorMessage error) {

        if (Objects.isNull(error.getStream())) {
            sendError(error);
        } else {

            List<String> sendErrorDTOS = developerService.getDeveloperChatId(error.getProjectId());
            SendDocument sendDocument = new SendDocument();


            InputFile inputFile = new InputFile(error.getStream(), "error");

            sendErrorDTOS.forEach((s) -> {
                sendDocument.setCaption("<b>Project: </b>" + error.getProjectName() + "\n<b>Time: </b>" + error.getHappenTime());
                sendDocument.setDocument(inputFile);
                bot.sendDocument(sendDocument);
            });
        }
    }

    public void sendError(ErrorMessage error) {

        List<String> chatIdList = developerService.getDeveloperChatId(error.getProjectId());

        File file;
        FileWriter fileWriter;
        String prefix = "error";
        String suffix = ".log";

        File directoryPath = new File("/Users/macbookair/Documents/PDP/Java backend/ErrorManager/src/main/resources/sendFile");


        try {
            file = File.createTempFile(prefix, suffix, directoryPath);
            fileWriter = new FileWriter(file);
            fileWriter.write(error.getErrorText());
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        SendDocument sendDocument = new SendDocument();

        InputFile inputFile = new InputFile(file);

        System.out.println(inputFile);

        for (String s : chatIdList) {
            sendDocument.setCaption("<b>Project: </b>" + error.getProjectName() + "\n<b>Time: </b>" + error.getHappenTime());
            sendDocument.setDocument(inputFile);
            sendDocument.setChatId(s);
            bot.sendDocument(sendDocument);
        }
//        chatIdList.forEach((chatId) -> {
//
//            sendDocument.setCaption("<b>Project name:</b>" + error.getProjectName() + "<br><b>Time</b>:" + System.currentTimeMillis());
//            sendDocument.setDocument(inputFile);
//            sendDocument.setChatId(chatId);
//            bot.sendDocument(sendDocument);
//
//        });
    }
}
