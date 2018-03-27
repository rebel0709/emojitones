package com.emojitones.keyboard.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.emojitones.keyboard.R;
import com.emojitones.keyboard.adapter.EmojitonesAdapter;

public  class EmojitonesFragment extends Fragment {


    String[] people_emojitones= new String[]{"people1","people2","people3","people4","people5","people6","people7","people8","people9","people10","people11 halo","people12","people13","people14",
            "people15 relieved","people16","people17","people18","people19","people20 kiss3","people21","people22","people23","people24","people25a crazy face","people26a raised eyebrow","people27a monicle",
            "people26","people27","people28","people30a starStruck","people31","people32","people33","people34","people35 worried","people36","people37","people38","people39","people40 confounded","people41",
            "people42","people43","people44","people45 redface","people46","people47","people48","people48a symbolMouth","people49","people49a explodingHead","people50","people51","people52","people53",
            "people54","people55 flushed face","people56","people57","people58","people58a hadOverMouth","people59","people59a shush","people60 disappointed","people61","people62","people63","people64",
            "people65","people66","people67","people68","people69","people70 zipper","people71","people77a vomit","people72","people73","people74","people75 doh","people25 dollars","people30 cowboy","people76",
            "people77","people78","people79","people29","people80 poo","people81","people82","people84","people85 alien monster","people86","people87","people88","people89","people90 tears of joy cat",
            "people91","people92","people93","people94","people95 tear cat","people96","people97","people98","people99","people100 amen","people102","people103","people104","people105 how","people109",
            "people110 finger horns","people111","people112","people113","people116 ah ah","people117","people121","people123","people124","people127","people128","people129","people130 kiss","people131",
            "people132","people133","people134","people135 feet","people137","people138","people141 baby","people142","people143","people144","people145 woman","people146","people147","people148","people150",
            "people152 Arab","people154","people156","people158","people160 spy","people194","people195","people197","people199","people202","people205","people207","people209","people210","people215 pouting",
            "people217","people219","people221","people223","people224","people226 tap","people229","people231 runner","people232","people233","people234","people235","people236 girl girl","people237",
            "people238","people239","people240","people241","people266","people267","people268","people269","people270 dress","people271","people272","people273","people274","people275 boots","people276",
            "people277","people278","people279","people280","people281","people282","people283","people284 pouch","people285","people286","people287","people288","people289","people290"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.tab_emojitones_fragment, container, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new EmojitonesAdapter(getContext(),people_emojitones));
        return rootView;
    }
}