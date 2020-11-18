package com.shufflrr.sdk;

/**
 * Encapsulates abstractions for core request types.
 */
public final class Requests {
    static final Request LOGIN = new Request(Request.Category.ACCOUNT, Request.Type.POST, "login", "Content-Type", "application/json", "Accept", "application/json");

    public static final Request FOLDERS = new Request(Request.Category.FOLDERS, Request.Type.GET, "", "Accept", "application/json");
    public static final Request ALL_FOLDERS = new Request(Request.Category.FOLDERS, Request.Type.GET, "all", "Accept", "application/json");
    public static final Request CREATE_FOLDER = new Request(Request.Category.FOLDERS, Request.Type.POST, "", "Content-Type", "application/json", "Accept", "application/json");
    public static final Request FOLDER_CONTENTS = new Request(Request.Category.FOLDERS, Request.Type.GET, "%s/contents", "Accept", "application/json");
    public static final Request FOLDER_UPDATE = new Request(Request.Category.FOLDERS, Request.Type.PUT, "");
    public static final Request FOLDER_MOVE = new Request(Request.Category.FOLDERS, Request.Type.POST, "%s/move", "Content-Type", "application/json", "Accept", "application/json");
    public static final Request FOLDER_DELETE = new Request(Request.Category.FOLDERS, Request.Type.DELETE, "%s", "Sec-Fetch-Site", "same-origin", "Sec-Fetch-Mode", "cors", "Sec-Fetch-Dest", "empty");
    public static final Request FOLDER_UPLOAD = new Request(Request.Category.FOLDERS, Request.Type.POST, "%s/upload", "Accept", "application/json");

    public static final Request ACTIONS_QUEUE = new Request(Request.Category.ACTIONS_QUEUE, Request.Type.GET, "", "Accept", "application/json");

    public static final Request FILES = new Request(Request.Category.FILES, Request.Type.GET, "", "Accept", "application/json");
    public static final Request FILE = new Request(Request.Category.FILES, Request.Type.GET, "%s", "Accept", "application/json");
    public static final Request FILE_UPDATE = new Request(Request.Category.FILES, Request.Type.PUT, "%s");
    public static final Request FILE_MOVE = new Request(Request.Category.FILES, Request.Type.PUT, "move", "Content-Type", "application/json");
    public static final Request FILE_DOWNLOAD = new Request(Request.Category.FILES, Request.Type.GET, "%s/download", "Accept", "application/json");
    public static final Request FILE_DELETE = new Request(Request.Category.FILES, Request.Type.DELETE, "%s", "Content-Type", "application/json");

    public static final Request PORTAL = new Request(Request.Category.PORTAL, Request.Type.GET, "", "Accept", "application/json");

    public static final Request PRESENTATION_SLIDES = new Request(Request.Category.PRESENTATIONS, Request.Type.GET, "%s/slides", "Accept", "application/json");

    public static final Request USER = new Request(Request.Category.USER, Request.Type.GET, "", "Accept", "application/json");

    private Requests() {}
}
