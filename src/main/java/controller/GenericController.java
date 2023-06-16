package controller;

import java.util.List;

public interface GenericController<T> {
    void save();
    void getAll();
    void getById();
    void update();
    void delete();
    void existById();
}
