package com.wj.core.http;

import java.nio.charset.UnsupportedCharsetException;
import java.util.LinkedHashMap;
import java.util.Map;

public class MediaType {

    private final String type;
    private final String subtype;
    private final Map<String, String> parameters;

    private static final String WILDCARD_TYPE = "*";
    public static final String APPLICATION_TYPE = "application";
    public static final String JSON_TYPE = "json";

    public static final MediaType APPLICATION_JSON = new MediaType(APPLICATION_TYPE, JSON_TYPE);

    MediaType(String type, String subtype) {
        this.type = type;
        this.subtype = subtype;
        this.parameters = null;
    }

    MediaType(String type, String subtype, Map<String, String> parameters) {
        this.type = type;
        this.subtype = subtype;
        this.parameters = parameters;
    }


    public static MediaType parseMediaType(String mimeType) {
        return parseMimeTypeInternal(mimeType);
    }

    private static MediaType parseMimeTypeInternal(String mimeType) {
        int index = mimeType.indexOf(';');
        String fullType = (index >= 0 ? mimeType.substring(0, index) : mimeType).trim();
        if (fullType.isEmpty()) {
            throw new InvalidMimeTypeException(mimeType, "'mimeType' must not be empty");
        }

        if (MediaType.WILDCARD_TYPE.equals(fullType)) {
            fullType = "*/*";
        }
        int subIndex = fullType.indexOf('/');
        if (subIndex == -1) {
            throw new InvalidMimeTypeException(mimeType, "does not contain '/'");
        }
        if (subIndex == fullType.length() - 1) {
            throw new InvalidMimeTypeException(mimeType, "does not contain subtype after '/'");
        }
        String type = fullType.substring(0, subIndex);
        String subtype = fullType.substring(subIndex + 1);
        if (MediaType.WILDCARD_TYPE.equals(type) && !MediaType.WILDCARD_TYPE.equals(subtype)) {
            throw new InvalidMimeTypeException(mimeType, "wildcard type is legal only in '*/*' (all mime types)");
        }

        Map<String, String> parameters = null;
        do {
            int nextIndex = index + 1;
            boolean quoted = false;
            while (nextIndex < mimeType.length()) {
                char ch = mimeType.charAt(nextIndex);
                if (ch == ';') {
                    if (!quoted) {
                        break;
                    }
                }
                else if (ch == '"') {
                    quoted = !quoted;
                }
                nextIndex++;
            }
            String parameter = mimeType.substring(index + 1, nextIndex).trim();
            if (parameter.length() > 0) {
                if (parameters == null) {
                    parameters = new LinkedHashMap<>(4);
                }
                int eqIndex = parameter.indexOf('=');
                if (eqIndex >= 0) {
                    String attribute = parameter.substring(0, eqIndex).trim();
                    String value = parameter.substring(eqIndex + 1).trim();
                    parameters.put(attribute, value);
                }
            }
            index = nextIndex;
        }
        while (index < mimeType.length());

        try {
            return new MediaType(type, subtype, parameters);
        }
        catch (UnsupportedCharsetException ex) {
            throw new InvalidMimeTypeException(mimeType, "unsupported charset '" + ex.getCharsetName() + "'");
        }
        catch (IllegalArgumentException ex) {
            throw new InvalidMimeTypeException(mimeType, ex.getMessage());
        }
    }

    public String getType() {
        return type;
    }

    public String getSubtype() {
        return subtype;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }
}
