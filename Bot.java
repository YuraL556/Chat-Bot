import java.util.*;
import java.util.regex.*;

public class Bot extends Calculator{

    final String[] COMMON_PHRASES = {
            "Немає нічого ціннішого слів, сказаних до місця і до часу.",
            "Часом мовчання може сказати більше, ніж безліч слів.",
            "Перед тим як писати / говорити завжди краще подумати.",
            "Ввічлива і грамотна мова говорить про велич душі.",
            "Приємно коли текст без орфографічних помилок.",
            "Багатослівність є ознака невпорядкованого розуму.",
            "Боюся Ви щось не договорюєте."};

    final String[] ELUSIVE_ANSWERS = {
            "Питання непросте, прошу тайм-аут на роздуми.",
            "Не впевнений, що маю таку інформацію.",
            "Може краще поговоримо про щось інше?",
            "Вибачте, але це дуже особисте питання.",
            "Не впевнений, що Вам сподобається відповідь.",
            "Повірте, я сам хотів би це знати.",
            "Ви дійсно хочете це знати?",
            "Упевнений, Ви вже здогадалися самі.",
            "Навіщо Вам така інформація?",
            "Давайте збережемо інтригу?"};

    final Map<String, String> PATTERNS_FOR_ANALYSIS = new HashMap<String, String>() {{
        // hello
        put("хай", "hello");
        put("привіт", "hello");
        put("хело", "hello");
        put("здрастуй", "hello");
        // who
        put("хто\\s.*ти", "who");
        put("ти\\s.*хто", "who");
        // name
        put("як\\s.*звати", "name");
        put("як\\s.*імя", "name");
        put("є\\s.*імя", "name");
        put("яке\\s.*імя", "name");
        // howareyou
        put("як\\s.*діла", "howareyou");
        put("як\\s.*справи", "howareyou");
        put("як\\s.*життя", "howareyou");
        // whatdoyoudoing
        put("навіщо\\s.*тут", "whatdoyoudoing");
        put("що\\s.*робиш", "whatdoyoudoing");
        put("чим\\s.*займаєшся", "whatdoyoudoing");
        // whatdoyoulike
        put("що\\s.*подобається", "whatdoyoulike");
        put("що\\s.*любиш", "whatdoyoulike");
        // iamfeelling
        put("здається", "iamfeelling");
        put("відчуваю", "iamfeelling");
        // yes
        put("^да", "yes");
        put("^так", "yes");
        put("^ок", "yes");
        put("згоден", "yes");
        // whattime
        put("котра\\s.*година", "whattime");
        put("яка\\s.*година", "whattime");
        put("який\\s.*час", "whattime");
        put("скільки\\s.*часу", "whattime");
        put("скільки\\s.*годин", "whattime");
        // bye
        put("прощай", "bye");
        put("бувай", "bye");
        put("пока", "bye");
        put("побачемось", "bye");
        put("до\\ s. * побачення", "bye");
        //joke
        put("анігдот","joke");
        put("шутку","joke");
        put("смішну\\s.*історію","joke");
        //bye
        put("бувай","bye");
        put("дозустрічі","bye");
        put("пока","bye");
    }};
    final Map<String, String> ANSWERS_BY_PATTERNS = new HashMap<String, String>() {{
        put("hello", "Привіт, радий Вас бачити.");
        put("who", "Я звичайний чат-бот.");
        put("name", "Називайте мене Чаттер :)");
        put("howareyou", "Спасибі, що цікавитеся. У мене все добре.");
        put("whatdoyoudoing", "Я пробую спілкуватися з людьми.");
        put("whatdoyoulike", "Мені пдоюається думати що я не просто програма.");
        put("iamfeelling", "Як давно це почалося? Розкажіть трохи докладніше.");
        put("yes", "Да так і живемо.");
        put("bye", "Бувай. Сподіваюсь ще побачимось.");
        put("joke","Хлопчик хуліган тиждень не міг потрапити додому.Він дзвонив в двері і тікав.");
        put("bye","Бувайте, було приємно поспілкуватись");
    }};
    Pattern pattern; // for regexp
    Random random; // for random answers
    Date date; // for date and time

    public Bot() {
        random = new Random();
        date = new Date();
    }

    public String sayInReturn(String msg, boolean ai) {
        String say = (msg.trim().endsWith("?")) ?
                ELUSIVE_ANSWERS[random.nextInt(ELUSIVE_ANSWERS.length)] :
                COMMON_PHRASES[random.nextInt(COMMON_PHRASES.length)];
        if (ai) {
            String message = String.join(" ", msg.toLowerCase().split("[ {,|.}?]+"));
            if (isDigitPresent(message)) return ParsCalculator(message);
            for (Map.Entry<String, String> o : PATTERNS_FOR_ANALYSIS.entrySet()) {
                pattern = Pattern.compile(o.getKey());
                if (pattern.matcher(message).find()) {
                    if (o.getValue().equals("whattime")) return date.toString();
                    else return ANSWERS_BY_PATTERNS.get(o.getValue());
                }
            }
        }
        return say;
    }
    private boolean isDigitPresent(String currencyCode) {
        char[] chars = currencyCode.toCharArray();

        for (char character : chars) {
            if (Character.isDigit(character)) {
                return true;
            }
        }
        return false;
    }
}