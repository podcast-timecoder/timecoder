package com.timecoder.service;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class SseService {

    private static List<SseEmitter> emitterList = new CopyOnWriteArrayList<>();

    public SseEmitter registerEmitter(SseEmitter emitter) {

        emitterList.add(emitter);
        emitter.onCompletion(() -> emitterList.remove(emitter));

        return emitter;
    }

    public void emitNotification(Object notification) {
        emitterList.forEach((SseEmitter emitter) -> {
            try {
                emitter.send(notification, MediaType.APPLICATION_JSON);
            } catch (IOException e) {
                emitter.complete();
                emitterList.remove(emitter);
                //e.printStackTrace();
            }
        });
    }

}
