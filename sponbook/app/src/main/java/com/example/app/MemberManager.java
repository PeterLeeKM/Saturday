package com.example.app;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class MemberManager {
    private static MemberManager instance = null;
    private ArrayList<Member> members = null;
    public static String path = Environment.getExternalStorageDirectory()+"/members.txt";

    private MemberManager() {
        this.members = this.getMembersFromFile();
    }
    public static MemberManager getInstance() {
        if(MemberManager.instance == null)
            MemberManager.instance = new MemberManager();
        return MemberManager.instance;
    }

    private ArrayList<Member> getMembersFromFile() {

        File source = new File(path);
        BufferedReader reader = null;
        ArrayList<Member> members = null;
        try {
            reader = new BufferedReader(new FileReader(source));
            members = new ArrayList<Member>();
            String content;
            String [] data = null;
            while(reader.ready()) {	// 변경
                content = reader.readLine();
                data = content.split("\t");
                Member member = new Member(data[0], data[1], data[2], data[3], Integer.parseInt(data[4]));
                members.add(member);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return members;
    }

    public Member findMember(String username){
        for(Member member : this.members){
            if(member.getUsername().equals(username)){
                return member;
            }
        }
        return null;
    }

    public void registerMember(Member member){
        writeNewMemberOnFile(member);
        this.members = getMembersFromFile();
    }

    private void writeNewMemberOnFile(Member member) {
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(path, true)));
            out.println(member.getUsername() + "\t" +
                    member.getPassword() + "\t" +
                    member.getGroupName() + "\t" +
                    member.getPhoneNumber() + "\t" +
                    String.valueOf(member.getGroupMemberCount()));
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
