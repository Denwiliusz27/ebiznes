describe('template spec', () => {

    it('check availability of main page', () => {
      cy.visit('https://www.otomoto.pl/');
      cy.contains('Akceptuję').click();
      cy.contains('Pokaż').should('exist');
    });

    it('create offer', () => {
      cy.visit('https://www.otomoto.pl/');
      cy.contains('Akceptuję').click();
      cy.get('.ooa-1hai5tz').contains('Zacznij sprzedawać').click();
      cy.contains('Utwórz ogłoszenie').should('exist').click()
    });

    it('clicking "pokaż" to show offers on new url', () => {
      cy.visit('https://www.otomoto.pl/');
      cy.contains('Pokaż').should('exist').click();
      cy.contains('Akceptuję').should('exist').click();
      cy.url().should('include', 'osobowe')
    });

    it('search by "typ nadwozia"', () => {
      cy.visit('https://www.otomoto.pl/');
      cy.contains('Akceptuję').click();
      cy.get('input[placeholder="Typ nadwozia"]').click()
      cy.contains('Auta małe').click();
      cy.contains('Pokaż').click();
      cy.url().should('include', 'seg-mini')
    });

    it('search by "model pojazdu"', () => {
      cy.visit('https://www.otomoto.pl/');
      cy.contains('Akceptuję').click();

      cy.get('input[placeholder="Marka pojazdu"]').click();
      cy.contains('Audi').click();

      cy.get('input[placeholder="Model pojazdu"]').click();
      cy.contains('A1').click();

      cy.contains('Pokaż').click();
      cy.url().should('include', 'osobowe/audi/a1')
    });

    it('search by "rodzaj paliwa"', () => {
      cy.visit('https://www.otomoto.pl/');
      cy.contains('Akceptuję').click();

      cy.get('input[placeholder="Rodzaj paliwa"]').click();
      cy.contains('Benzyna').click();

      cy.contains('Pokaż').click();
    });

    it('check if login is possible', () => {
      cy.visit('https://www.otomoto.pl/');
      cy.contains('Akceptuję').click();
      cy.contains('Zaloguj się').should('exist');
    });

    it('check if "oferta dnia" is visible', () => {
      cy.visit('https://www.otomoto.pl/');
      cy.contains('Akceptuję').click();
      cy.contains('Pokaż').click();
      cy.contains('Oferta dnia').should('exist');
    });

    it('check if "obserwuj" is visible', () => {
        cy.visit('https://www.otomoto.pl/');
        cy.contains('Akceptuję').click();
        cy.contains('Pokaż').click();
        cy.wait(500);
        cy.contains('Obserwuj').should('exist');
    });

    it('check if there are highlihted offers', () => {
        cy.visit('https://www.otomoto.pl/');
        cy.contains('Akceptuję').click();
        cy.contains('Pokaż').click();
        cy.wait(500);
        cy.contains('Wyróżnione').should('exist');
    });

    it('check if digital passport buy option is working', () => {
        cy.visit('https://www.otomoto.pl/');
        cy.contains('Akceptuję').click();
        cy.contains('Kup teraz online').should('be.visible').click();
    });

    it('check if observe offers are 0', () => {
        cy.visit('https://www.otomoto.pl/');
        cy.contains('Akceptuję').click();
        cy.contains('Obserwowane').should('exist');
        cy.get('.ooa-c269xo').should("have.text", "(0)");
    });

    it('check if observe offers are 0', () => {
        cy.visit('https://www.otomoto.pl/');
        cy.contains('Akceptuję').click();
        cy.contains('Pokaż').click();
        cy.get('.ooa-1cq16zj .evg565y20').first().click();
        cy.contains('Szczegóły').should('be.visible');
    });

    it('advanced search by "stan uszkodzenia"', () => {
        cy.visit('https://www.otomoto.pl/');
        cy.contains('Akceptuję').click();
        cy.contains('Wyszukiwanie zaawansowane').should('exist').click();
        cy.get('input[placeholder="Stan uszkodzeń"]').click()
        cy.contains('Nieuszkodzony').should('exist').click();
    });

    it('advanced search by "program specjalny"', () => {
        cy.visit('https://www.otomoto.pl/');
        cy.contains('Akceptuję').click();
        cy.contains('Wyszukiwanie zaawansowane').should('exist').click();
        cy.get('input[placeholder="Programy specjalne"]').click();
        cy.contains('Volvo Selekt').should('exist').click();
    });

    it('sort offers', () => {
        cy.visit('https://www.otomoto.pl/');
        cy.contains('Akceptuję').click();
        cy.contains('Pokaż').should('exist').click();
        cy.get('button[value="Wyróżnione"]').click()
        cy.contains('Najnowsze').should('exist').click();
    });

    it('search by "Używane"', () => {
        cy.visit('https://www.otomoto.pl/');
        cy.contains('Akceptuję').click();
        cy.contains('Pokaż').should('exist').click();
        cy.contains('Używane').should('be.visible').click()
    });

    it('search by "Nowe"', () => {
        cy.visit('https://www.otomoto.pl/');
        cy.contains('Akceptuję').click();
        cy.contains('Pokaż').should('exist').click();
        cy.contains('Nowe').should('be.visible').click()
    });
})
