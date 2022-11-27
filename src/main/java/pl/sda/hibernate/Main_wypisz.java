package pl.sda.hibernate;

import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import pl.sda.hibernate.model.Ocena;
import pl.sda.hibernate.model.Student;

import java.util.List;
import java.util.Scanner;

public class Main_wypisz {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Co wypisać (studentów / oceny):");
        String wypisanaEncja = scanner.nextLine();

        try (Session session = HibernateUtil.INSTANCE.getSessionFactory().openSession()) {
            if (wypisanaEncja.equalsIgnoreCase("student")) {
                // wypisz studentów
                TypedQuery<Student> zapytanie = session.createQuery("FROM Student", Student.class);
                List<Student> lista = zapytanie.getResultList();
                lista.forEach(System.out::println);

            } else if (wypisanaEncja.equalsIgnoreCase("ocena")) {
                // wypisz oceny
                TypedQuery<Ocena> zapytanie = session.createQuery("FROM Ocena", Ocena.class);
                List<Ocena> lista = zapytanie.getResultList();
                lista.forEach(System.out::println);

            } else {
                System.err.println("Nieznana komenda");
            }
        } catch (Exception e) {
            System.err.println("Błąd dodawania studenta do bazy");
        }
    }
}