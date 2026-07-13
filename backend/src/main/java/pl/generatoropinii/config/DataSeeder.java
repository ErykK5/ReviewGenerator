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
 * NOTE: the SenMeasure section reproduces the full SEN tab from the original
 * spreadsheet (69 entries across ~19 categories). Two rows under RUCH ("movement")
 * were left as-is: the spreadsheet enabled them (B=1) but never filled in their
 * recommendation text, so there was nothing to import for that category.
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
            seedSenMeasures();
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

        // BUG FIX: this was seeded as "Visual Assembly" while the frontend/pairs use "Visual Puzzles",
        // so this recommendation could never match a submitted subtest result.
        recommendation("Visual Puzzles", 8,
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

    private void seedSenMeasures() {
        sen("ACCOMMODATIONS", "Adjusting requirements", 1,
                "dostosowanie wymaga\u0144 edukacyjnych do indywidualnych potrzeb i mo\u017cliwo\u015bci psychofizycznych ucznia, z uwzgl\u0119dnieniem specyficznych trudno\u015bci w uczeniu si\u0119,");

        sen("ACCOMMODATIONS", "Exemption from second foreign language", 2,
                "zwolnienie ucznia z nauki drugiego j\u0119zyka obcego nowo\u017cytnego na wniosek rodzic\u00f3w, zgodnie z obowi\u0105zuj\u0105cymi przepisami prawa o\u015bwiatowego.");

        sen("ACTIVITIES", "Psychological-pedagogical support", 1,
                "obj\u0119cie pomoc\u0105 psychologiczno-pedagogiczn\u0105 w formie, kt\u00f3r\u0105 ucz\u0105cy nauczyciele w porozumieniu z psychologiem szkolnym uznaj\u0105 za najw\u0142a\u015bciwsz\u0105.");

        sen("ACTIVITIES", "Corrective-compensatory classes", 2,
                "udzia\u0142 w zaj\u0119ciach korekcyjno-kompensacyjnych w celu stymulowania funkcji poznawczych, w tym pami\u0119ci i koncentracji uwagi, rozwijania funkcji wzrokowo-przestrzennych, s\u0142uchowo-j\u0119zykowych, sprawno\u015bci grafomotorycznej, wykonywania \u0107wicze\u0144 doskonal\u0105cych znajomo\u015b\u0107 zasad ortograficznych oraz umiej\u0119tno\u015bci czytania ze zrozumieniem, wed\u0142ug rozpoznania nauczyciela,");

        sen("ACTIVITIES", "Remedial classes", 3,
                "obj\u0119cie zaj\u0119ciami dydaktyczno-wyr\u00f3wnawczymi z przedmiot\u00f3w, w zakresie kt\u00f3rych wyst\u0119puj\u0105 trudno\u015bci w nauce,");

        sen("ACTIVITIES", "Corrective-compensatory or remedial classes (as needed)", 4,
                "zaj\u0119cia dydaktyczno-wyr\u00f3wnawcze i/lub korekcyjno-kompensacyjne wg rozpoznania nauczycieli,");

        sen("ACTIVITIES", "Speech therapy", 5,
                "obj\u0119cie zaj\u0119ciami logopedycznymi w celu wspierania rozwoju mowy, komunikacji j\u0119zykowej oraz usprawniania zaburzonych funkcji,");

        sen("ACTIVITIES", "Psychological support", 6,
                "udzielanie uczniowi wsparcia psychologicznego w trakcie bie\u017c\u0105cej pracy, zgodnie z jego indywidualnymi potrzebami,");

        sen("ACTIVITIES", "Emotional-social competence classes", 7,
                "obj\u0119cie zaj\u0119ciami rozwijaj\u0105cymi kompetencje emocjonalno-spo\u0142eczne w celu wspierania rozwoju samoregulacji emocjonalnej, radzenia sobie z trudnymi emocjami oraz rozwijania umiej\u0119tno\u015bci spo\u0142ecznych,");

        sen("METHODS", "Guidelines for attention difficulties", 1,
                "stosowanie wskaza\u0144 do pracy z uczniem z trudno\u015bciami w koncentracji uwagi, w szczeg\u00f3lno\u015bci: stosowanie kr\u00f3tkich, prostych polece\u0144 i instrukcji, powtarzanie polece\u0144 i upewnianie si\u0119, czy ucze\u0144 poprawnie je zrozumia\u0142, ograniczenie liczby bod\u017ac\u00f3w i stresuj\u0105cych sytuacji, dzielenie materia\u0142u na mniejsze cz\u0119\u015bci, stworzenie uczniowi uporz\u0105dkowanego \u015brodowiska,");

        sen("METHODS", "Guidelines for ADHD", 2,
                "stosowanie wskaza\u0144 do pracy z uczniem z trudno\u015bciami w koncentracji uwagi oraz nadpobudliwo\u015bci psychoruchowej, w szczeg\u00f3lno\u015bci: stosowanie kr\u00f3tkich, prostych polece\u0144 i instrukcji, upewnianie si\u0119, czy ucze\u0144 poprawnie je zrozumia\u0142, dzielenie pracy na etapy, ograniczenie liczby bod\u017ac\u00f3w, stworzenie uczniowi uporz\u0105dkowanego \u015brodowiska (warto wprowadzi\u0107 sta\u0142y plan zaj\u0119\u0107 oraz jasne zasady),");

        sen("METHODS", "Multisensory teaching methods", 3,
                "stosowanie metod polisensorycznych anga\u017cuj\u0105cych r\u00f3\u017cne kana\u0142y percepcji (wzrokowy, s\u0142uchowy, ruchowy i dotykowy), w celu u\u0142atwiania przyswajania, utrwalania i wykorzystywania nowych wiadomo\u015bci i umiej\u0119tno\u015bci,");

        sen("METHODS", "Activating teaching methods", 4,
                "stosowanie metod aktywizuj\u0105cych oraz umo\u017cliwianie uczniowi zdobywania wiedzy poprzez praktyczne dzia\u0142anie, do\u015bwiadczenie, eksperymentowanie i rozwi\u0105zywanie problem\u00f3w,");

        sen("EXTENDED TIME", "Extended time for written work", 1,
                "wyd\u0142u\u017cenie czasu pracy podczas prac pisemnych, pracy z tekstem oraz sprawdzian\u00f3w i kartk\u00f3wek, zgodnie z indywidualnymi potrzebami ucznia,");

        sen("UNDERSTANDING INSTRUCTIONS", "Clear, short instructions", 1,
                "stosowanie jasnych, kr\u00f3tkich i jednoznacznych polece\u0144 oraz sprawdzanie, czy ucze\u0144 w\u0142a\u015bciwie rozumie instrukcj\u0119,");

        sen("UNDERSTANDING INSTRUCTIONS", "Additional explanations and guiding questions", 2,
                "stosowanie dodatkowych wyja\u015bnie\u0144, powt\u00f3rze\u0144 oraz pyta\u0144 naprowadzaj\u0105cych u\u0142atwiaj\u0105cych rozumienie polece\u0144 z\u0142o\u017conych,");

        sen("UNDERSTANDING INSTRUCTIONS", "Graduated task difficulty and breaks", 3,
                "stopniowanie trudno\u015bci zada\u0144, dzielenie pracy na etapy oraz stosowanie cz\u0119stszych przerw,");

        sen("ATTENTION", "Organized, low-distraction workspace", 1,
                "zapewnienie uporz\u0105dkowanego i przewidywalnego \u015brodowiska pracy, ograniczenie nadmiernych bod\u017ac\u00f3w rozpraszaj\u0105cych oraz organizowanie miejsca pracy sprzyjaj\u0105cego koncentracji uwagi,");

        sen("ATTENTION", "Seating for optimal focus", 2,
                "zapewnienie miejsca w klasie umo\u017cliwiaj\u0105cego optymalny odbi\u00f3r wypowiedzi nauczyciela i \u015bledzenia toku zaj\u0119\u0107,");

        sen("ATTENTION", "Building perseverance", 3,
                "kszta\u0142towanie wytrwa\u0142o\u015bci w dzia\u0142aniu, zwracanie uwagi na doprowadzanie rozpocz\u0119tych zada\u0144 do ko\u0144ca,");

        sen("ATTENTION", "Frequent activity changes", 4,
                "wprowadzanie cz\u0119stych zmian aktywno\u015bci oraz akcentowanie najwa\u017cniejszych informacji,");

        sen("ATTENTION", "Sensory aids for focus", 5,
                "umo\u017cliwienie uczniowi korzystania z pomocy sensorycznych (np. gniotk\u00f3w, pi\u0142eczek antystresowych, mas plastycznych, wa\u0142eczk\u00f3w/poduszek sensorycznych, przedmiot\u00f3w manipulacyjnych) wspieraj\u0105cych koncentracj\u0119 uwagi i samoregulacj\u0119,");

        sen("DYSORTHOGRAPHY", "Spelling errors excluded from grading", 1,
                "aspekt poprawno\u015bci ortograficznej i interpunkcyjnej nie powinien wp\u0142ywa\u0107 na ocen\u0119 ko\u0144cow\u0105 prac pisemnych, a pope\u0142niane b\u0142\u0119dy powinny stanowi\u0107 materia\u0142 do dalszej pracy ucznia nad doskonaleniem poprawnego zapisu,");

        sen("DYSORTHOGRAPHY", "Self-checking of written work", 2,
                "kszta\u0142towanie umiej\u0119tno\u015bci samokontroli prac pisemnych poprzez wdra\u017canie do samodzielnego sprawdzania poprawno\u015bci zapisu oraz wyszukiwania i poprawiania b\u0142\u0119d\u00f3w,");

        sen("DYSLEXIA", "Avoiding reading aloud in class", 1,
                "unikanie sytuacji z odpytywania g\u0142o\u015bnego czytania na forum klasy,");

        sen("DYSLEXIA", "Checking comprehension during independent work", 2,
                "kontrolowanie stopnia rozumienia czytanego tekstu oraz polece\u0144 podczas samodzielnej pracy ucznia,");

        sen("DYSLEXIA", "Auditory processing support techniques", 3,
                "stosowanie technik i \u015brodk\u00f3w wspieraj\u0105cych przetwarzanie s\u0142uchowe, np. metody \u201epowt\u00f3rz po mnie\u201d, s\u0142uchowisk, audiobook\u00f3w oraz innych materia\u0142\u00f3w d\u017awi\u0119kowych, w celu zapewnienia uczniowi cz\u0119stszej i bardziej intensywnej ekspozycji na j\u0119zyk oraz wspierania rozwoju kompetencji j\u0119zykowych,");

        sen("DYSGRAPHIA", "Handwriting not graded", 1,
                "charakter pisma nie powinien podlega\u0107 ocenie; podczas oceniania prac pisemnych nale\u017cy uwzgl\u0119dnia\u0107 przede wszystkim tre\u015b\u0107 i poziom merytoryczny wypowiedzi ucznia,");

        sen("DYSGRAPHIA", "Computer use for longer texts", 2,
                "umo\u017cliwienie korzystania z komputera podczas pisania d\u0142u\u017cszych tekst\u00f3w,");

        sen("DYSGRAPHIA", "Relaxed grading of handwriting neatness", 3,
                "dostosowanie wymaga\u0144 edukacyjnych w zakresie poziomu graficznego pisma - estetyka i wygl\u0105d zapisu nie powinny stanowi\u0107 istotnego kryterium oceny prac pisemnych ucznia,");

        sen("DYSGRAPHIA", "Accepting print or manuscript writing", 4,
                "akceptowanie pisma bibliotecznego lub zapisu literami drukowanymi,");

        sen("DYSGRAPHIA", "Oral clarification of illegible work", 5,
                "w przypadku trudno\u015bci z odczytaniem fragment\u00f3w prac pisemnych umo\u017cliwienie uczniowi uzupe\u0142nienia lub wyja\u015bnienia tre\u015bci w formie odpowiedzi ustnej,");

        sen("DYSCALCULIA", "Individual grading criteria for mathematics", 1,
                "stosowanie indywidualnych kryteri\u00f3w wymaga\u0144 i oceniania w zakresie matematyki oraz innych przedmiot\u00f3w \u015bcis\u0142ych,");

        sen("DYSCALCULIA", "Math aid tools allowed (tables, formulas)", 2,
                "umo\u017cliwienie korzystania z dodatkowych pomocy u\u0142atwiaj\u0105cych rozwi\u0105zywanie zada\u0144 matematycznych (np. tabliczki mno\u017cenia, wzor\u00f3w, algorytm\u00f3w rozwi\u0105za\u0144),");

        sen("DYSCALCULIA", "Accommodations when assessing math skills", 3,
                "uwzgl\u0119dnianie trudno\u015bci w uczeniu si\u0119 matematyki podczas sprawdzania wiedzy i umiej\u0119tno\u015bci poprzez: dostosowanie liczby i stopnia trudno\u015bci zada\u0144, sprawdzanie rozumienia polece\u0144, ocenianie ca\u0142ego procesu rozwi\u0105zywania zadania, a nie wy\u0142\u0105cznie ko\u0144cowego wyniku, zwolnienie z konieczno\u015bci pami\u0119ciowego opanowania wzor\u00f3w, zasad i regu\u0142 oraz umo\u017cliwienie korzystania z dodatkowych pomocy (np. kalkulatora, tablic ze wzorami, w\u0142asnor\u0119cznie przygotowanych notatek zawieraj\u0105cych wzory, przeliczenia i strategie rozwi\u0105zywania zada\u0144),");

        sen("DYSCALCULIA", "Monitoring math progress", 4,
                "monitorowanie post\u0119p\u00f3w ucznia w zakresie umiej\u0119tno\u015bci matematycznych oraz podejmowanie adekwatnych dzia\u0142a\u0144 wspieraj\u0105cych; w przypadku utrzymuj\u0105cych si\u0119 trudno\u015bci wskazana jest konsultacja w Poradni Psychologiczno-Pedagogicznej,");

        sen("MEMORY AND LEARNING", "Visual memory support", 1,
                "stosowanie \u0107wicze\u0144 i metod wspieraj\u0105cych rozw\u00f3j pami\u0119ci wzrokowej, m.in. poprzez wykorzystywanie materia\u0142\u00f3w graficznych, schemat\u00f3w, tabel, map my\u015bli, ilustracji oraz zaznaczanie najwa\u017cniejszych informacji w tek\u015bcie,");

        sen("MEMORY AND LEARNING", "Auditory memory support", 2,
                "stosowanie \u0107wicze\u0144 wspieraj\u0105cych rozw\u00f3j pami\u0119ci s\u0142uchowej, m.in. poprzez powtarzanie i utrwalanie informacji, dzielenie materia\u0142u na mniejsze partie, rytmizacj\u0119, g\u0142o\u015bne powtarzanie oraz wykorzystywanie skojarze\u0144 u\u0142atwiaj\u0105cych zapami\u0119tywanie,");

        sen("MEMORY AND LEARNING", "Repetition and reinforcement", 3,
                "wspieranie procesu zapami\u0119tywania poprzez cz\u0119ste powt\u00f3rzenia, systematyczne utrwalanie materia\u0142u, odwo\u0142ywanie si\u0119 do wcze\u015bniej zdobytej wiedzy oraz stosowanie r\u00f3\u017cnorodnych sposob\u00f3w kodowania informacji,");

        sen("MEMORY AND LEARNING", "Multisensory memory techniques", 4,
                "stosowanie metod polisensorycznych anga\u017cuj\u0105cych r\u00f3\u017cne kana\u0142y percepcji (wzrok, s\u0142uch, ruch), wspieraj\u0105cych zapami\u0119tywanie, rozumienie i utrwalanie nowych wiadomo\u015bci i umiej\u0119tno\u015bci,");

        sen("MEMORY AND LEARNING", "Structuring material with graphic aids", 5,
                "pomoc uczniowi w porz\u0105dkowaniu i strukturyzowaniu materia\u0142u poprzez tworzenie schemat\u00f3w, notatek graficznych, plan\u00f3w dzia\u0142ania oraz wskazywanie najwa\u017cniejszych informacji wymagaj\u0105cych zapami\u0119tania,");

        sen("MEMORY AND LEARNING", "Pacing information delivery", 6,
                "dostosowanie ilo\u015bci przekazywanych jednorazowo informacji do mo\u017cliwo\u015bci ucznia, dzielenie z\u0142o\u017conych instrukcji na kr\u00f3tsze etapy oraz sprawdzanie stopnia zapami\u0119tania i rozumienia przekazywanych tre\u015bci,");

        sen("EXECUTIVE FUNCTIONS", "Planning and organization skills", 1,
                "wspieranie rozwoju umiej\u0119tno\u015bci planowania i organizowania pracy poprzez uczenie ustalania kolejno\u015bci dzia\u0142a\u0144, przygotowywania potrzebnych materia\u0142\u00f3w oraz korzystania z plan\u00f3w, harmonogram\u00f3w i list kontrolnych,");

        sen("EXECUTIVE FUNCTIONS", "Encouraging task follow-through", 2,
                "wzmacnianie samodzielno\u015bci ucznia poprzez rozwijanie umiej\u0119tno\u015bci rozpoczynania, kontynuowania i doprowadzania rozpocz\u0119tych zada\u0144 do ko\u0144ca, z wykorzystaniem adekwatnych wskaz\u00f3wek i wsparcia,");

        sen("EXECUTIVE FUNCTIONS", "Cognitive flexibility", 3,
                "rozwijanie elastyczno\u015bci poznawczej poprzez stwarzanie sytuacji wymagaj\u0105cych poszukiwania r\u00f3\u017cnych sposob\u00f3w rozwi\u0105zania problemu, przewidywania konsekwencji dzia\u0142a\u0144 oraz dokonywania wyboru adekwatnej strategii,");

        sen("EXECUTIVE FUNCTIONS", "Impulse control", 4,
                "wspieranie rozwoju kontroli impuls\u00f3w poprzez uczenie ucznia zatrzymywania si\u0119 przed podj\u0119ciem dzia\u0142ania, analizowania sytuacji oraz przewidywania mo\u017cliwych konsekwencji w\u0142asnych zachowa\u0144,");

        sen("MOTIVATION", "Positive reinforcement", 1,
                "stosowanie wzmocnie\u0144 pozytywnych,");

        sen("MOTIVATION", "Consistent, predictable reward system", 2,
                "stosowanie sp\u00f3jnego i przewidywalnego systemu pozytywnych wzmocnie\u0144 (np. pochwa\u0142, informacji zwrotnej, ustalonych nagr\u00f3d lub przywilej\u00f3w) w celu zwi\u0119kszania motywacji  oraz wytrwa\u0142o\u015bci w dzia\u0142aniu,");

        sen("MOTIVATION", "Creating opportunities for success", 3,
                "tworzenie sytuacji umo\u017cliwiaj\u0105cych osi\u0105ganie sukces\u00f3w, wzmacnianie poczucia sprawstwa oraz motywowanie do podejmowania wysi\u0142ku poprzez dostrzeganie mocnych stron,");

        sen("EMOTION REGULATION", "Building on strengths and interests", 1,
                "wykorzystywanie mocnych stron, zainteresowa\u0144 i zasob\u00f3w ucznia w celu wzmacniania poczucia kompetencji i pozytywnego obrazu siebie,");

        sen("EMOTION REGULATION", "Recognizing and naming emotions", 2,
                "wspieranie ucznia w rozpoznawaniu, nazywaniu i wyra\u017caniu emocji poprzez stosowanie adekwatnych do wieku metod i \u0107wicze\u0144,");

        sen("EMOTION REGULATION", "Coping strategies for stress", 3,
                "uczenie skutecznych sposob\u00f3w radzenia sobie z napi\u0119ciem, stresem i sytuacjami trudnymi, np. technik oddechowych, przerw regulacyjnych, aktywno\u015bci ruchowej lub innych indywidualnie dobranych sposob\u00f3w,");

        sen("EMOTION REGULATION", "Reinforcing effort and progress", 4,
                "stwarzanie uczniowi sytuacji umo\u017cliwiaj\u0105cych osi\u0105ganie sukces\u00f3w, wzmacnianie poczucia sprawstwa oraz budowanie przekonania o w\u0142asnych mo\u017cliwo\u015bciach poprzez docenianie wysi\u0142ku, post\u0119p\u00f3w i podejmowanych pr\u00f3b,");

        sen("EMOTION REGULATION", "Highlighting strengths and achievements", 5,
                "podkre\u015blanie mocnych stron i osi\u0105gni\u0119\u0107 oraz wspieranie rozwoju adekwatnej samooceny,");

        sen("EMOTION REGULATION", "Adult support in difficult situations", 6,
                "zapewnienie wsparcia osoby doros\u0142ej w sytuacjach trudnych,");

        sen("SOCIAL DEVELOPMENT", "Building peer relationships", 1,
                "wspieranie w rozwijaniu umiej\u0119tno\u015bci nawi\u0105zywania, podtrzymywania i budowania pozytywnych relacji z r\u00f3wie\u015bnikami poprzez organizowanie sytuacji sprzyjaj\u0105cych wsp\u00f3\u0142pracy i komunikacji,");

        sen("SOCIAL DEVELOPMENT", "Understanding group rules", 2,
                "wspieranie w rozumieniu i przestrzeganiu zasad obowi\u0105zuj\u0105cych w grupie oraz przewidywaniu konsekwencji w\u0142asnych zachowa\u0144,");

        sen("SOCIAL DEVELOPMENT", "Communicating needs and emotions", 3,
                "rozwijanie umiej\u0119tno\u015bci komunikowania w\u0142asnych potrzeb, opinii i emocji w spos\u00f3b akceptowany spo\u0142ecznie,");

        sen("SOCIAL DEVELOPMENT", "Cooperative games and activities", 4,
                "stwarzanie okazji do podejmowania wsp\u00f3\u0142pracy z r\u00f3wie\u015bnikami oraz rozwijania umiej\u0119tno\u015bci osi\u0105gania wsp\u00f3lnych cel\u00f3w poprzez gry i zabawy kooperacyjne,");

        sen("SOCIAL DEVELOPMENT", "Conflict resolution skills", 5,
                "uczenie konstruktywnych sposob\u00f3w rozwi\u0105zywania konflikt\u00f3w, wyra\u017cania niezadowolenia, negocjowania oraz poszukiwania kompromisowych rozwi\u0105za\u0144,");

        sen("SOCIAL DEVELOPMENT", "Initiating social contact", 6,
                "wspieranie ucznia w inicjowaniu kontakt\u00f3w spo\u0142ecznych oraz zwi\u0119kszaniu pewno\u015bci siebie w sytuacjach wymagaj\u0105cych kontaktu z innymi osobami,");

        sen("SOCIAL DEVELOPMENT", "Reinforcing positive social behavior", 7,
                "stosowanie pozytywnych wzmocnie\u0144 za podejmowanie w\u0142a\u015bciwych zachowa\u0144 spo\u0142ecznych, wsp\u00f3\u0142prac\u0119, przestrzeganie zasad oraz podejmowanie pr\u00f3b nawi\u0105zywania kontaktu z innymi osobami,");

        sen("SOCIAL DEVELOPMENT", "Fostering tolerance and acceptance", 8,
                "kszta\u0142towanie postawy akceptacji wobec r\u00f3\u017cnic mi\u0119dzy lud\u017ami, rozwijanie tolerancji oraz umiej\u0119tno\u015bci funkcjonowania w zr\u00f3\u017cnicowanym \u015brodowisku spo\u0142ecznym,");

        sen("SHYNESS AND WITHDRAWAL", "Encouraging group participation", 1,
                "zach\u0119canie do podejmowania kontakt\u00f3w spo\u0142ecznych oraz udzia\u0142u w aktywno\u015bciach grupowych, np. wolontariacie, harcerstwie, akcjach charytatywnych lub innych dzia\u0142aniach zespo\u0142owych, sprzyjaj\u0105cych rozwijaniu kompetencji spo\u0142ecznyc i poczucia przynale\u017cno\u015bci,");

        sen("SHYNESS AND WITHDRAWAL", "Gradual exposure to public speaking", 2,
                "unikanie przymuszania do wyst\u0105pie\u0144 publicznych i prezentowania swoich wypowiedzi na forum klasy; umo\u017cliwianie stopniowego oswajania si\u0119 z sytuacjami ekspozycji spo\u0142ecznej,");

        sen("INTERESTS", "Developing interests and talents", 1,
                "rozwijanie zainteresowa\u0144 i uzdolnie\u0144 przez udzia\u0142 w dodatkowych aktywno\u015bciach, ko\u0142ach zainteresowa\u0144, projektach edukacyjnych lub zaj\u0119ciach pozaszkolnych,");

        sen("MONITORING", "Ongoing monitoring and PPP referral", 1,
                "monitorowanie sytuacji edukacyjnej i wychowawczej ucznia, modyfikowanie oddzia\u0142ywa\u0144 zgodnie z aktualnymi potrzebami, a w razie utrzymuj\u0105cych si\u0119 trudno\u015bci wskazany kontakt z PPP,");

        sen("COOPERATION", "Cooperation with parents", 1,
                "wsp\u00f3\u0142praca z rodzicami celem ujednolicenia oddzia\u0142ywa\u0144 edukacyjnych i wychowawczych,");

        sen("EXAM ACCOMMODATIONS", "Exam accommodations per CKE guidelines", 1,
                "dostosowanie warunk\u00f3w i form przeprowadzania egzamin\u00f3w ko\u0144cz\u0105cych poszczeg\u00f3lne etapy edukacyjne do aktualnie obowi\u0105zuj\u0105cych przepis\u00f3w oraz wytycznych Centralnej Komisji Egzaminacyjnej dotycz\u0105cych uczni\u00f3w ze specyficznymi trudno\u015bciami w uczeniu si\u0119,");
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
