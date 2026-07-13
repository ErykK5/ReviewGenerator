package pl.generatoropinii.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.generatoropinii.model.*;
import pl.generatoropinii.repository.*;

/**
 * Populates the database with configuration data matching the formulas of the
 * original WISC-KLIKADLO.xlsx spreadsheet 1:1, run only once (when the database
 * is empty).
 *
 * NOTE: the content strings below (interpretations, comparisons, recommendations)
 * are kept in Polish on purpose - they are the text that ends up in the final
 * generated report (see ReportGenerationService), which is a Polish-language
 * psychological-pedagogical document. Everything else (class/field/method names,
 * table names, API routes) is in English.
 *
 * NOTE: the SenMeasure section only contains a representative fraction of the
 * SEN database (the spreadsheet had around 80 entries across a dozen or so
 * categories). It's worth eventually adding an import of the full SEN tab,
 * e.g. via a script that reads the original xlsx.
 */
@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final WiscIndexThresholdRepository wiscIndexThresholdRepository;
    private final SubtestPairRepository subtestPairRepository;
    private final SubtestRecommendationRepository subtestRecommendationRepository;
    private final SenMeasureRepository senMeasureRepository;

    @Override
    public void run(String... args) {
        if (wiscIndexThresholdRepository.count() == 0) {
            seedIndexThresholds();
        }
        if (subtestPairRepository.count() == 0) {
            seedSubtestPairs();
        }
        if (subtestRecommendationRepository.count() == 0) {
            seedSubtestRecommendations();
        }
        if (senMeasureRepository.count() == 0) {
            seedSenMeasuresFragment();
        }
    }

    private void seedIndexThresholds() {
        threshold(WiscIndex.FSIQ, 130, "Og\u00f3lna sprawno\u015b\u0107 intelektualna plasuje si\u0119 na poziomie inteligencji bardzo wysokiej.");
        threshold(WiscIndex.FSIQ, 120, "Og\u00f3lna sprawno\u015b\u0107 intelektualna plasuje si\u0119 na poziomie inteligencji wysokiej.");
        threshold(WiscIndex.FSIQ, 110, "Og\u00f3lna sprawno\u015b\u0107 intelektualna plasuje si\u0119 na poziomie inteligencji powy\u017cej przeci\u0119tnej.");
        threshold(WiscIndex.FSIQ, 90, "Og\u00f3lna sprawno\u015b\u0107 intelektualna plasuje si\u0119 na poziomie inteligencji przeci\u0119tnej.");
        threshold(WiscIndex.FSIQ, 80, "Og\u00f3lna sprawno\u015b\u0107 intelektualna plasuje si\u0119 na poziomie inteligencji poni\u017cej przeci\u0119tnej.");
        threshold(WiscIndex.FSIQ, 70, "Og\u00f3lna sprawno\u015b\u0107 intelektualna plasuje si\u0119 na poziomie pogranicza niepe\u0142nosprawno\u015bci.");
        threshold(WiscIndex.FSIQ, Integer.MIN_VALUE, "Og\u00f3lna sprawno\u015b\u0107 intelektualna plasuje si\u0119 na poziomie niepe\u0142nosprawno\u015bci intelektualnej.");

        threshold(WiscIndex.VCI, 115, "Rozumowanie s\u0142owne, czyli zdolno\u015b\u0107 abstrahowania, ekspresji werbalnej oraz umiej\u0119tno\u015b\u0107 nabywania i wydobywania z pami\u0119ci s\u0142\u00f3w i poj\u0119\u0107 plasuje si\u0119 na poziomie wysokim.");
        threshold(WiscIndex.VCI, 86, "Rozumowanie s\u0142owne, czyli zdolno\u015b\u0107 abstrahowania, ekspresji werbalnej oraz umiej\u0119tno\u015b\u0107 nabywania i wydobywania z pami\u0119ci s\u0142\u00f3w i poj\u0119\u0107 plasuje si\u0119 na poziomie przeci\u0119tnym.");
        threshold(WiscIndex.VCI, Integer.MIN_VALUE, "Rozumowanie s\u0142owne, czyli zdolno\u015b\u0107 abstrahowania, ekspresji werbalnej oraz umiej\u0119tno\u015b\u0107 nabywania i wydobywania z pami\u0119ci s\u0142\u00f3w i poj\u0119\u0107 jest obni\u017cona.");

        threshold(WiscIndex.VSI, 115, "Integracja wzrokowo-ruchowa, zdolno\u015b\u0107 do analizy i syntezy wzrokowej oraz umiej\u0119tno\u015b\u0107 dostrzegania relacji przestrzennych plasuj\u0105 si\u0119 na poziomie wysokim.");
        threshold(WiscIndex.VSI, 86, "Integracja wzrokowo-ruchowa, zdolno\u015b\u0107 do analizy i syntezy wzrokowej oraz umiej\u0119tno\u015b\u0107 dostrzegania relacji przestrzennych plasuj\u0105 si\u0119 na poziomie przeci\u0119tnym.");
        threshold(WiscIndex.VSI, Integer.MIN_VALUE, "Integracja wzrokowo-ruchowa, zdolno\u015b\u0107 do analizy i syntezy wzrokowej oraz umiej\u0119tno\u015b\u0107 dostrzegania relacji przestrzennych plasuj\u0105 si\u0119 na poziomie niskim.");

        threshold(WiscIndex.FRI, 115, "Ucze\u0144 prezentuje wysokie umiej\u0119tno\u015bci w zakresie rozwi\u0105zywania zada\u0144 logicznych, polegaj\u0105cych na odkrywaniu relacji mi\u0119dzy elementami z\u0142o\u017conych uk\u0142ad\u00f3w i wykrywaniu zasad, na jakich s\u0105 one oparte. Rozumowanie ilo\u015bciowe jest bardzo dobrze rozwini\u0119te.");
        threshold(WiscIndex.FRI, 86, "Ucze\u0144 na poziomie adekwatnym do wieku opanowa\u0142 umiej\u0119tno\u015b\u0107 rozwi\u0105zywania zada\u0144 logicznych, polegaj\u0105cych na odkrywaniu relacji mi\u0119dzy elementami z\u0142o\u017conych uk\u0142ad\u00f3w i wykrywaniu zasad, na jakich s\u0105 one oparte. Rozumowanie ilo\u015bciowe jest prawid\u0142owe.");
        threshold(WiscIndex.FRI, Integer.MIN_VALUE, "U ucznia obserwuje si\u0119 trudno\u015bci w zakresie rozwi\u0105zywania zada\u0144 logicznych, odkrywania relacji mi\u0119dzy elementami uk\u0142ad\u00f3w oraz wykrywania zasad, na jakich s\u0105 one oparte. Rozumowanie ilo\u015bciowe jest obni\u017cone.");

        threshold(WiscIndex.WMI, 115, "Na poziomie wysokim plasuje si\u0119 zakres pami\u0119ci roboczej. \u015awiadczy to o bardzo dobrze rozwini\u0119tej zdolno\u015bci przechowywania, analizowania i przekszta\u0142cania informacji wzrokowych i s\u0142uchowych oraz sprawnym wykorzystywaniu ich podczas wykonywania z\u0142o\u017conych zada\u0144 poznawczych.");
        threshold(WiscIndex.WMI, 86, "Na poziomie przeci\u0119tnym plasuje si\u0119 zakres pami\u0119ci roboczej. \u015awiadczy to o prawid\u0142owym przebiegu rozwoju tej kategorii proces\u00f3w pami\u0119ciowych, w kt\u00f3rych r\u00f3\u017cnorodne informacje wzrokowe i s\u0142uchowe umieszczone w pami\u0119ci kr\u00f3tkotrwa\u0142ej s\u0105 sprawdzane, grupowane i przekszta\u0142cane.");
        threshold(WiscIndex.WMI, Integer.MIN_VALUE, "Na poziomie niskim plasuje si\u0119 zakres pami\u0119ci roboczej. \u015awiadczy to o trudno\u015bciach w zakresie przechowywania, przetwarzania i wykorzystywania informacji wzrokowych oraz s\u0142uchowych w pami\u0119ci kr\u00f3tkotrwa\u0142ej. Ucze\u0144 mo\u017ce wymaga\u0107 dodatkowego wsparcia podczas wykonywania z\u0142o\u017conych i wieloetapowych zada\u0144.");

        threshold(WiscIndex.PSI, 115, "Ucze\u0144 bardzo dobrze radzi sobie z zadaniami wymagaj\u0105cymi szybko\u015bci przetwarzania, czyli umiej\u0119tno\u015bci sprawnego i trafnego rozpoznawania wzrokowego oraz szybkiego podejmowania prostych decyzji. Tempo pracy i sprawno\u015b\u0107 wzrokowo-ruchowa s\u0105 dobrze rozwini\u0119te.");
        threshold(WiscIndex.PSI, 86, "Ucze\u0144 dobrze radzi sobie z zadaniami wymagaj\u0105cymi szybko\u015bci przetwarzania, czyli umiej\u0119tno\u015bci sprawnego i trafnego rozpoznawania wzrokowego oraz szybkiego podejmowania prostych decyzji.");
        threshold(WiscIndex.PSI, Integer.MIN_VALUE, "Ucze\u0144 mo\u017ce do\u015bwiadcza\u0107 trudno\u015bci w zakresie szybko\u015bci przetwarzania informacji, obejmuj\u0105cej sprawne i trafne rozpoznawanie wzrokowe oraz szybkie podejmowanie prostych decyzji. Wskazane jest zapewnienie dodatkowego czasu na wykonanie zada\u0144 wymagaj\u0105cych tempa pracy i koncentracji uwagi.");
    }

    private void threshold(WiscIndex wiscIndex, int lowerThreshold, String text) {
        WiscIndexThreshold t = new WiscIndexThreshold();
        t.setWiscIndex(wiscIndex);
        t.setLowerThreshold(lowerThreshold);
        t.setInterpretationText(text);
        wiscIndexThresholdRepository.save(t);
    }

    private void seedSubtestPairs() {
        pair(WiscIndex.VCI, "Similarities", "Vocabulary", 2.17,
                "Ucze\u0144 lepiej radzi sobie z abstrahowaniem i kategoryzowaniem poj\u0119\u0107 ni\u017c z ich definiowaniem.",
                "Ucze\u0144 lepiej radzi sobie z definiowaniem poj\u0119\u0107 ni\u017c z ich kategoryzowaniem.");

        // Note: in the original spreadsheet, the difference formula for this pair was
        // also incorrect (it referenced C5 instead of D3) - here we use the correct values for both subtests.
        pair(WiscIndex.VSI, "Block Design", "Visual Puzzles", 2.30,
                "Ucze\u0144 lepiej radzi sobie z analiz\u0105 i organizacj\u0105 materia\u0142u wzrokowo-przestrzennego ni\u017c z syntez\u0105 wzrokow\u0105.",
                "Ucze\u0144 lepiej radzi sobie z syntez\u0105 wzrokow\u0105 i dostrzeganiem relacji mi\u0119dzy elementami ni\u017c z analiz\u0105 i organizacj\u0105 materia\u0142u wzrokowego.");

        pair(WiscIndex.FRI, "Matrix Reasoning", "Figure Weights", 1.73,
                "Ucze\u0144 sprawniej wykonuje zadania wymagaj\u0105ce dostrzegania relacji i prawid\u0142owo\u015bci w materiale wzrokowym oraz rozumowania abstrakcyjnego ni\u017c zadania anga\u017cuj\u0105ce rozumowanie ilo\u015bciowe.",
                "Ucze\u0144 sprawniej wykonuje zadania wymagaj\u0105ce rozumowania ilo\u015bciowego i rozumienia relacji mi\u0119dzy wielko\u015bciami ni\u017c zadania polegaj\u0105ce na odkrywaniu abstrakcyjnych regu\u0142 w materiale wzrokowym.");

        pair(WiscIndex.WMI, "Digit Span", "Picture Span", 2.09,
                "W zakresie pami\u0119ci roboczej zaznacza si\u0119 przewaga pami\u0119ci s\u0142uchowej nad wzrokow\u0105. Ucze\u0144 sprawniej zapami\u0119tuje i przetwarza informacje prezentowane drog\u0105 s\u0142uchow\u0105 ni\u017c materia\u0142 wzrokowy.",
                "W zakresie pami\u0119ci roboczej zaznacza si\u0119 przewaga pami\u0119ci wzrokowej nad s\u0142uchow\u0105. Ucze\u0144 sprawniej zapami\u0119tuje i odtwarza informacje prezentowane w materiale obrazowym ni\u017c informacje podawane drog\u0105 s\u0142uchow\u0105.");

        pair(WiscIndex.PSI, "Coding", "Symbol Search", 3.01,
                "Ucze\u0144 sprawniej wykonuje zadania anga\u017cuj\u0105ce umiej\u0119tno\u015b\u0107 szybkiego uczenia si\u0119 poprzez skojarzenia oraz sprawno\u015b\u0107 grafomotoryczn\u0105 ni\u017c zadania wymagaj\u0105ce spostrzegawczo\u015bci wzrokowej.",
                "Ucze\u0144 sprawniej wykonuje zadania wymagaj\u0105ce szybkiego przeszukiwania pola wzrokowego oraz spostrzegawczo\u015bci wzrokowej ni\u017c zadania anga\u017cuj\u0105ce sprawno\u015b\u0107 grafomotoryczn\u0105 oraz umiej\u0119tno\u015b\u0107 uczenia si\u0119 wzrokowo-ruchowego.");
    }

    private void pair(WiscIndex scale, String a, String b, double threshold, String textA, String textB) {
        SubtestPair p = new SubtestPair();
        p.setScale(scale);
        p.setSubtestA(a);
        p.setSubtestB(b);
        p.setSignificanceThreshold(threshold);
        p.setTextWhenAGreater(textA);
        p.setTextWhenBGreater(textB);
        subtestPairRepository.save(p);
    }

    private void seedSubtestRecommendations() {
        recommendation("Similarities", 8,
                "\u2022 rozwijanie umiej\u0119tno\u015bci klasyfikowania, kategoryzowania oraz dostrzegania podobie\u0144stw i r\u00f3\u017cnic mi\u0119dzy poj\u0119ciami poprzez \u0107wiczenia j\u0119zykowe, zadania problemowe oraz wyja\u015bnianie znacze\u0144,\n" +
                "\u2022 wspieranie rozwoju my\u015blenia abstrakcyjnego oraz wzbogacanie zasobu s\u0142ownictwa poprzez zach\u0119canie do uzasadniania odpowiedzi,");

        recommendation("Vocabulary", 8,
                "\u2022 wzbogacanie zasobu s\u0142ownictwa i rozwijanie rozumienia poj\u0119\u0107,\n" +
                "\u2022 \u0107wiczenie definiowania poj\u0119\u0107 i formu\u0142owania pe\u0142nych wypowiedzi.\n" +
                "\u2022 stymulowanie kompetencji j\u0119zykowych poprzez rozmowy i zadania s\u0142owne.");

        // BUG FIX: the original formula checked the "Similarities" score instead of "Block Design".
        recommendation("Block Design", 8,
                "\u2022 rozwijanie orientacji przestrzennej, analizy i syntezy wzrokowej poprzez uk\u0142adanie klock\u00f3w, puzzli oraz prac\u0119 z materia\u0142em obrazkowym,\n" +
                "\u2022 \u0107wiczenie odwzorowywania wzor\u00f3w oraz planowania przestrzennego,\n" +
                "\u2022 wspieranie koordynacji wzrokowo-ruchowej i sprawno\u015bci grafomotorycznej,");

        recommendation("Visual Assembly", 8,
                "\u2022 rozwijanie syntezy wzrokowej oraz umiej\u0119tno\u015bci tworzenia ca\u0142o\u015bci z element\u00f3w poprzez uk\u0142adanie puzzli, tangram\u00f3w i mozaik,\n" +
                "\u2022 \u0107wiczenie wyobra\u017ani przestrzennej oraz dostrzegania relacji mi\u0119dzy elementami,\n" +
                "\u2022 wspieranie elastycznego my\u015blenia i rozwi\u0105zywania zada\u0144 wymagaj\u0105cych manipulowania materia\u0142em wzrokowym.");

        recommendation("Matrix Reasoning", 8,
                "\u2022 stymulowanie rozumowania indukcyjnego oraz my\u015blenia abstrakcyjnego poprzez zadania logiczne, \u0142amig\u0142\u00f3wki, sekwencje obrazkowe i odkrywanie regu\u0142,\n" +
                "\u2022 rozwijanie umiej\u0119tno\u015bci dostrzegania zale\u017cno\u015bci, analizowania relacji mi\u0119dzy elementami oraz wyci\u0105gania wniosk\u00f3w poprzez zadania problemowe i gry strategiczne,\n" +
                "\u2022 wspieranie elastycznego my\u015blenia oraz samodzielnego poszukiwania sposob\u00f3w rozwi\u0105zania problem\u00f3w poprzez r\u00f3\u017cnorodne \u0107wiczenia wymagaj\u0105ce logicznego wnioskowania,");

        recommendation("Figure Weights", 8,
                "\u2022 rozwijanie logicznego my\u015blenia i rozumowania matematycznego poprzez zadania wymagaj\u0105ce por\u00f3wnywania, szacowania i dostrzegania zale\u017cno\u015bci,\n" +
                "\u2022 \u0107wiczenie wyci\u0105gania wniosk\u00f3w oraz rozwi\u0105zywania zada\u0144 problemowych poprzez sytuacje praktyczne i zadania matematyczne,\n" +
                "\u2022 wspieranie samodzielnego planowania sposobu rozwi\u0105zania zadania oraz poszukiwania r\u00f3\u017cnych strategii dzia\u0142ania,");

        recommendation("Digit Span", 8,
                "\u2022 stymulowanie pami\u0119ci s\u0142uchowej poprzez wielokrotne powtarzanie najwa\u017cniejszych informacji, stosowanie powt\u00f3rze\u0144 i utrwalania materia\u0142u,\n" +
                "\u2022 stosowanie kr\u00f3tkich, jasnych komunikat\u00f3w oraz upewnianie si\u0119, \u017ce ucze\u0144 rozumie polecenie i wie, co ma robi\u0107 w danym momencie,\n" +
                "\u2022 dzielenie z\u0142o\u017conych zada\u0144 na mniejsze etapy oraz stopniowanie trudno\u015bci zada\u0144,\n" +
                "\u2022 stosowanie dodatkowych instrukta\u017cy i obja\u015bnie\u0144 w celu u\u0142atwienia ich zrozumienia,\n" +
                "\u2022 wspieranie samodzielno\u015bci ucznia poprzez stosowanie plan\u00f3w pracy, list kontrolnych i przypominajek,");

        recommendation("Picture Span", 8,
                "\u2022 stymulowanie pami\u0119ci wzrokowej poprzez wykorzystywanie materia\u0142\u00f3w obrazkowych, schemat\u00f3w, ilustracji, map my\u015bli i notatek wizualnych,\n" +
                "\u2022 ograniczanie liczby bod\u017ac\u00f3w podczas pracy oraz zapewnianie uczniowi warunk\u00f3w sprzyjaj\u0105cych skupieniu uwagi,\n" +
                "\u2022 stosowanie powt\u00f3rze\u0144, podkre\u015blanie najwa\u017cniejszych informacji oraz wykorzystywanie polisensorycznych metod nauczania, anga\u017cuj\u0105cych wzrok, s\u0142uch i dzia\u0142anie praktyczne, w celu u\u0142atwienia uczniowi przyswajania i utrwalania nowych informacji,\n" +
                "\u2022 uczenie strategii wspomagaj\u0105cych zapami\u0119tywanie, np. tworzenia skojarze\u0144, wyobra\u017ce\u0144, map my\u015bli i kr\u00f3tkich notatek,\n" +
                "\u2022 dzielenie materia\u0142u na mniejsze cz\u0119\u015bci oraz stopniowe wprowadzanie nowych informacji,");

        recommendation("Coding", 8,
                "\u2022 dostosowanie tempa pracy do mo\u017cliwo\u015bci ucznia oraz zapewnienie dodatkowego czasu na wykonanie zada\u0144,\n" +
                "\u2022 wspieranie przyswajania nowych tre\u015bci poprzez prezentowanie wzor\u00f3w, schemat\u00f3w oraz instrukcji krok po kroku,\n" +
                "\u2022 stosowanie cz\u0119stszych powt\u00f3rek i utrwalania materia\u0142u w celu u\u0142atwienia zapami\u0119tywania,\n" +
                "\u2022 rozwijanie koordynacji wzrokowo-ruchowej poprzez \u0107wiczenia grafomotoryczne, odwzorowywanie wzor\u00f3w oraz zadania wymagaj\u0105ce precyzji ruch\u00f3w,\n" +
                "\u2022 dzielenie d\u0142u\u017cszych zada\u0144 na mniejsze etapy oraz stosowanie kr\u00f3tkich przerw podczas pracy wymagaj\u0105cej d\u0142u\u017cszego utrzymania koncentracji uwagi,\n" +
                "\u2022 ograniczenie bod\u017ac\u00f3w rozpraszaj\u0105cych oraz ukierunkowanie uwagi ucznia na najwa\u017cniejsze elementy wykonywanego zadania,");

        recommendation("Symbol Search", 8,
                "\u2022 rozwijanie umiej\u0119tno\u015bci dostrzegania szczeg\u00f3\u0142\u00f3w i r\u00f3\u017cnic mi\u0119dzy elementami poprzez \u0107wiczenia z materia\u0142em obrazkowym, wyszukiwanie r\u00f3\u017cnic, uk\u0142adanki oraz zadania wzrokowe,\n" +
                "\u2022 wspieranie selekcji informacji wzrokowych poprzez \u0107wiczenia wymagaj\u0105ce wyszukiwania okre\u015blonych element\u00f3w w\u015br\u00f3d wielu bod\u017ac\u00f3w oraz kierowania uwagi na najwa\u017cniejsze informacje,\n" +
                "\u2022 ograniczanie bod\u017ac\u00f3w rozpraszaj\u0105cych, stosowanie kr\u00f3tkich i jasnych instrukcji oraz dzielenie d\u0142u\u017cszych zada\u0144 na mniejsze etapy,\n" +
                "\u2022 dostosowanie tempa pracy do mo\u017cliwo\u015bci ucznia oraz zapewnienie dodatkowego czasu na wykonanie zada\u0144,\n" +
                "\u2022 rozwijanie sprawno\u015bci przeszukiwania pola wzrokowego poprzez \u0107wiczenia polegaj\u0105ce na wyszukiwaniu symboli, znak\u00f3w, liter, r\u00f3\u017cnic mi\u0119dzy obrazkami oraz por\u00f3wnywaniu informacji wzrokowych,");
    }

    private void recommendation(String subtest, int thresholdBelow, String text) {
        SubtestRecommendation r = new SubtestRecommendation();
        r.setSubtest(subtest);
        r.setThresholdBelow(thresholdBelow);
        r.setRecommendationText(text);
        subtestRecommendationRepository.save(r);
    }

    private void seedSenMeasuresFragment() {
        sen("ACCOMMODATIONS", "Adjusting requirements", 1,
                "dostosowanie wymaga\u0144 edukacyjnych do indywidualnych potrzeb i mo\u017cliwo\u015bci psychofizycznych ucznia, z uwzgl\u0119dnieniem specyficznych trudno\u015bci w uczeniu si\u0119,");

        sen("ACTIVITIES", "Psychological-pedagogical support", 1,
                "obj\u0119cie pomoc\u0105 psychologiczno-pedagogiczn\u0105 w formie, kt\u00f3r\u0105 ucz\u0105cy nauczyciele w porozumieniu z psychologiem szkolnym uznaj\u0105 za najw\u0142a\u015bciwsz\u0105.");

        sen("ACTIVITIES", "Corrective-compensatory classes", 2,
                "udzia\u0142 w zaj\u0119ciach korekcyjno-kompensacyjnych w celu stymulowania funkcji poznawczych, w tym pami\u0119ci i koncentracji uwagi, rozwijania funkcji wzrokowo-przestrzennych, s\u0142uchowo-j\u0119zykowych, sprawno\u015bci grafomotorycznej, wykonywania \u0107wicze\u0144 doskonal\u0105cych znajomo\u015b\u0107 zasad ortograficznych oraz umiej\u0119tno\u015bci czytania ze zrozumieniem, wed\u0142ug rozpoznania nauczyciela,");

        sen("DYSLEXIA", "Avoiding reading aloud in class", 1,
                "unikanie sytuacji z odpytywania g\u0142o\u015bnego czytania na forum klasy,");

        sen("DYSGRAPHIA", "Handwriting not graded", 1,
                "charakter pisma nie powinien podlega\u0107 ocenie; podczas oceniania prac pisemnych nale\u017cy uwzgl\u0119dnia\u0107 przede wszystkim tre\u015b\u0107 i poziom merytoryczny wypowiedzi ucznia,");

        sen("DYSCALCULIA", "Individual grading criteria for mathematics", 1,
                "stosowanie indywidualnych kryteri\u00f3w wymaga\u0144 i oceniania w zakresie matematyki oraz innych przedmiot\u00f3w \u015bcis\u0142ych,");

        sen("MOTIVATION", "Positive reinforcement", 1,
                "stosowanie wzmocnie\u0144 pozytywnych,");

        // ... the remaining ~70 entries from the SEN tab should be added in the same way
        // (or via a script that imports them directly from the original xlsx).
    }

    private void sen(String category, String name, int displayOrder, String recommendation) {
        SenMeasure m = new SenMeasure();
        m.setCategory(category);
        m.setName(name);
        m.setDisplayOrder(displayOrder);
        m.setRecommendationForSchool("\u2022 " + recommendation);
        m.setRecommendationForParent("\u2022 " + recommendation);
        senMeasureRepository.save(m);
    }
}
