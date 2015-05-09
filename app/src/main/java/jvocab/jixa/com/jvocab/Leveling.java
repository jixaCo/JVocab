package jvocab.jixa.com.jvocab;


import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import jvocab.jixa.com.jvocab.Cache.DatabaseManager;
import jvocab.jixa.com.jvocab.Model.Word;

public class Leveling {
    DatabaseManager dbm;
    Leveling LVLInstance;
    public static int NUM_WORD_PER_STAGE;
    // True Factor
    int[] TF;
    // False Factor
    int[] FF;

    private Leveling(){
        NUM_WORD_PER_STAGE = 5;
    }

    public Leveling getInstance() {
        if (null == LVLInstance)
            return LVLInstance = new Leveling();
        return LVLInstance;
    }

    public List<Word> getWords(Context context) {
        int interval = 3000;
        List<Word> words = new ArrayList<>();
        int t = 0;
        int sum = 0;
        dbm = DatabaseManager.getInstance();
        for (int i = 0; i < 10000; i += interval) {
            for (int j = 0; j < NUM_WORD_PER_STAGE; j++) {
                int rnd = i + (int) (Math.random() * ((interval) + 1));
                words.add(dbm.getWordById(context, rnd));
                TF[t] = rnd;
                FF[t++] = 10000 - rnd;
                sum += rnd;
            }
            interval -= 500;
        }
        for (int i = 0; i < NUM_WORD_PER_STAGE * 5; i++) {
            TF[i] = TF[i] * (10000 / sum);
            FF[i] = (int) (FF[i] * (10000 / (NUM_WORD_PER_STAGE * 5 * 10000 - sum)) * 0.5);
        }
        return words;
    }

    public int calculateLevel(boolean[] answers) {
        int result = 0;
        for (int i = 0; i <= NUM_WORD_PER_STAGE * 5; i++) {
            if (answers[i]) {
                result += TF[i] ;
            } else {
                result -= FF[i] ;
            }
        }
        return result;
    }
}