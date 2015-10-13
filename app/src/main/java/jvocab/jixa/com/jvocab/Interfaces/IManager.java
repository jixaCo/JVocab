package jvocab.jixa.com.jvocab.Interfaces;

import java.util.List;

public interface IManager<T> {

    List<T> getAllMember();
    void add(T newMember);
    T getMemberByIndex(int index);
}
