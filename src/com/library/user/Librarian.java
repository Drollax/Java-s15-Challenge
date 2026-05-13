package com.library.user;

public class Librarian extends Person {
    private String password;

    public Librarian(long personId, String name, String password) {
        super(personId, name);
        this.password = password;
    }

    public boolean verifyMember(MemberRecord member) {
        if (member == null) {
            System.out.println("Hata: Üye kaydı bulunamadı.");
            return false;
        }

        if (member.getMemberId() != null && !member.getName().isEmpty()) {
            System.out.println("Üye doğrulandı: " + member.getName());
            return true;
        }

        System.out.println("Üye doğrulaması başarısız.");
        return false;
    }

    @Override
    public void whoYouAre() {
        System.out.println("Ben kütüphane görevlisiyim. Adım: " + getName());
    }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
