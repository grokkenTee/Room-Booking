package com.example.bookingroom.util;

import com.example.bookingroom.common.RoomStatus;
import com.example.bookingroom.common.RoomType;

import java.util.Arrays;
import java.util.List;

/**
 * Utility for getting model (model helper)
 */
public class ModelUtil {
    public static List<RoomStatus> listRoomStatus() {
        return Arrays.asList(RoomStatus.values());
    }

    public static List<RoomType> listRoomType() {
        return Arrays.asList(RoomType.values());
    }

    private ModelUtil() {
    }
}
