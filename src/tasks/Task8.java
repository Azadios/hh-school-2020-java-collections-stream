package tasks;

import common.Person;
import common.Task;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
А теперь о горьком
Всем придется читать код
А некоторым придется читать код, написанный мною (Уверен, он не так уж и плох на самом деле ;) )
Сочувствую им
Спасите будущих жертв, и исправьте здесь все, что вам не по душе!
P.S. функции тут разные и рабочие (наверное), но вот их понятность и эффективность страдает (аж пришлось писать комменты)
P.P.S Здесь ваши правки желательно прокомментировать (можно на гитхабе в пулл реквесте)
 */
public class Task8 implements Task {

  //Не хотим выдывать апи нашу фальшивую персону, поэтому конвертим начиная со второй
  public List<String> getNames(List<Person> persons) {
    // imho isEmpty более читабельно
    if (persons.isEmpty()) {
      return Collections.emptyList();
    }
    // Никчему менять входную коллекцию, да и imho skip читабельнее.
    // К тому же, возможно, remove будет O(n), а внутри stream'а
    // будет оптимизировано удаление первого элемента из List'а (но это не точно :) )
    return persons.stream()
          .skip(1)
          .map(Person::getFirstName)
          .collect(Collectors.toList());
  }

  //ну и различные имена тоже хочется
  public Set<String> getDifferentNames(List<Person> persons) {
    // Не понимаю к чему distinct, set сам по себе даст уникальные значения
    return new HashSet<>(getNames(persons));
  }

  // Немного сомнительная фича в том плане, что если это общепринятая форма Person to String,
  // то почему бы не сделать метод Person.toString, но ок, скажем мы не можем менять исходники Person
  // Ну и сам факт того, что объекты могут быть null, вместо того чтобы быть пустыми объектами (i.e "")
  // not cool man
  //Для фронтов выдадим полное имя, а то сами не могут
  public String convertPersonToString(Person person) {
    String result = "";
    if (person.getSecondName() != null) {
      result += person.getSecondName();
    }

    if (person.getFirstName() != null) {
      result += " " + person.getFirstName();
    }

    // Дубликат. Предположу, что имелось в виду middleName
    // Стоило бы узнать наверняка, а не предполагать, но кто вообще будет использовать это?
    if (person.getMiddleName() != null) {
      result += " " + person.getMiddleName();
    }
    return result;
  }

  // словарь id персоны -> ее имя
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {
    //Сомнительная оптимизация памяти :)
    Map<Integer, String> map = new HashMap<>();
    for (Person person : persons) {
      // К чему изобретать велосипед, верно?
      map.putIfAbsent(person.getId(), convertPersonToString(person));
    }
    return map;
  }

  // есть ли совпадающие в двух коллекциях персоны?
  // Если бы это был метод persons1 с аргументом persons2, то ок,
  // но аргумента у нас 2, а значит множественное число
  // и вроде как have, а не has правильно, но я не силён в английский. Скорее на подумать.
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    return !Collections.disjoint(persons1, persons2);
  }

  //...
  //...
  public long countEven(Stream<Integer> numbers) {
    return numbers.filter(num -> num % 2 == 0).count();
  }

  @Override
  public boolean check() {
    System.out.println("А вот и не слабо!");
    // :D
    // boolean codeSmellsGood = false;
    // boolean reviewerDrunk = false;
    boolean thatWasFun = true;
    return thatWasFun;
  }
}
