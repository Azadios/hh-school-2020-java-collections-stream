package tasks;

import common.Person;
import common.PersonService;
import common.Task;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/*
Задача 1
Метод на входе принимает List<Integer> id людей, ходит за ними в сервис
(он выдает несортированный Set<Person>, внутренняя работа сервиса неизвестна)
нужно их отсортировать в том же порядке, что и переданные id.
Оценить асимпотику работы
 */
public class Task1 implements Task {

  // Алгоритмическая сложность O(n)
  // Потребление памяти O(n)
  private List<Person> findOrderedPersons(List<Integer> personIds) {
    // Предположительно, поиск кажого Person не зависит от их общего числа в запросе
    // (по крайней мере не в худшую сторону), иначе это довольно странный поиск imo
    // Алгоритмическая сложность O(n)
    // Потребление памяти O(n)
    Set<Person> persons = PersonService.findPersons(personIds);

    // Алгоритмическая сложность O(n) (Проход по Set O(n), добавление в Map амортизированное O(1))
    // Потребление памяти O(n)
    Map<Integer, Person> idToPerson = persons.stream()
                                      .collect(Collectors.toMap(Person::getId, Function.identity()));

    // Алгоритмическая сложность O(n) (Проход по List O(n), поиск по Map и добавление в List амортизированные O(1))
    // Потребление памяти O(n)
    List<Person> personsSortedById = personIds.stream()
                                    .map(id -> idToPerson.get(id))
                                    .collect(Collectors.toList());

    return personsSortedById;
  }

  @Override
  public boolean check() {
    List<Integer> ids = List.of(1, 2, 3);

    return findOrderedPersons(ids).stream()
        .map(Person::getId)
        .collect(Collectors.toList())
        .equals(ids);
  }

}
