package library.service;

import library.model.Genre;
import java.util.Scanner;

public class GenreServiceImpl implements GenreService {
    Scanner scanner = new Scanner(System.in);

    @Override
    public Genre genreChoice() {
        System.out.println("Выберите жанр из предложенных:\n1.Криминал\n2.Детектив\n3.Наука\n4.Фэнтэзи" +
                "\n5.Роман\n6.Ужасы\n7.Классика\n8.Триллер\n9.Другое");
        while (true) {
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        return Genre.CRIME;
                    case 2:
                        return Genre.DETECTIVE;
                    case 3:
                        return Genre.SCIENCE;
                    case 4:
                        return Genre.FANTASY;
                    case 5:
                        return Genre.ROMANCE;
                    case 6:
                        return Genre.HORROR;
                    case 7:
                        return Genre.CLASSIC;
                    case 8:
                        return Genre.THRILLER;
                    case 9:
                        return Genre.ANOTHER;
                }
                if (choice < 1 && choice > 9) {
                    System.out.println("Вы ввели неверное число, попробуйте ещё раз:");
                }
            } catch (NumberFormatException e) {
                System.out.println("Вы ввели неверный формат, попробуйте ещё раз:");
            }
        }
    }
}
