package com.nsu.rpgstats.entities;

import java.util.Objects;

public class Slot {
    private int id;
    private String name;
    private String iconUrl;
    private boolean isWhitelisted;
    private int characterId;
    private int itemId;
    private Item item = null;

    public Slot(int id, String name, String iconUrl, boolean isWhitelisted, int characterId, int itemId) {
        this.id = id;
        this.name = name;
        this.iconUrl = iconUrl;
        this.isWhitelisted = isWhitelisted;
        this.characterId = characterId;
        this.itemId = itemId;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public boolean isWhitelisted() {
        return isWhitelisted;
    }

    public void setWhitelisted(boolean whitelisted) {
        isWhitelisted = whitelisted;
    }

    public int getCharacterId() {
        return characterId;
    }

    public void setCharacterId(int characterId) {
        this.characterId = characterId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Slot slot = (Slot) o;
        return id == slot.id && isWhitelisted == slot.isWhitelisted && characterId == slot.characterId && itemId == slot.itemId && Objects.equals(name, slot.name) && Objects.equals(iconUrl, slot.iconUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, iconUrl, isWhitelisted, characterId, itemId);
    }
}
