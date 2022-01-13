package helpers

import io.codearte.jfairy.Fairy
import io.codearte.jfairy.producer.person.PersonProperties
import org.joda.time.LocalDate

import java.text.DateFormatSymbols

class Faker {
    Fairy fairy

    Faker() {
        fairy = Fairy.create()
    }

    Fairy getRandom() { this.fairy }

    String getFullName() {
        fairy.person(PersonProperties.male()).getFullName()
    }

    String getFemaleFirstName() {
        fairy.person(PersonProperties.female()).getFirstName()
    }

    String getPassword() {
        fairy.person(PersonProperties.withUsername("Amado")).getPassword()
    }

    String getMaleFirstName() {
        fairy.person(PersonProperties.male()).getFirstName()
    }

    String getMaleLastName() {
        fairy.person(PersonProperties.male()).getLastName()
    }

    String getRandomNumber() {
        fairy.person(PersonProperties.male()).getNationalIdentityCardNumber()
    }

    String getAge() {
        String.valueOf(fairy.person(PersonProperties.minAge(14)).getAge())
    }

    String getCity() {
        fairy.person(PersonProperties.female()).getAddress().getCity()
    }

    String getCityMalePerson() {
        fairy.person(PersonProperties.male()).getAddress().getCity()
    }

    String getEmail() {
        fairy.person().getEmail()
    }

    String getPhone() {
        fairy.person().getTelephoneNumber()
    }

    String getAddress() {
        fairy.person().getAddress().getAddressLine1()
    }

    String getWebsite() { fairy.company().getUrl() }

    def getRandomMessage = { fairy.textProducer().sentence() }

    RandomDate getRandomDate() {
        return new RandomDate()
    }

    private String getMonthName(int monthNumber) {
        String month = "wrong"
        DateFormatSymbols monthsNames = new DateFormatSymbols()
        String[] months = monthsNames.getMonths()
        if (monthNumber >= 0 && monthNumber <= 11) {
            month = months[monthNumber]
        }
        month
    }

    class RandomDate {
        private LocalDate localDate
        private String day
        private String month
        private String year

        RandomDate() {
            localDate = fairy.person(PersonProperties.withAge(30)).getDateOfBirth().toLocalDate()
            this.day = String.valueOf(localDate.getDayOfMonth())
            this.month = getMonthName(localDate.getMonthOfYear())
            this.year = String.valueOf(localDate.getYear())
        }
        String day() {
            day
        }
        String month() {
            month
        }
        String year() {
            year
        }
    }
}
