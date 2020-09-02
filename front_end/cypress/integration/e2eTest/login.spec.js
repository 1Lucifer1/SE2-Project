describe('Login Test', function() {

    const email = Math.floor((Math.random()*10000)+1) + "@qq.com";
    const password = "123456";

    beforeEach(()=>{
        cy.visit('/');
        cy.reload();
    });

    it('test register module ', function() {
        const birthday = '2000-01-01';
        const phoneNumber = '12345678901';
        cy.get('[aria-selected="false"]').click();
        //cy.get('#registerBirthday').click();
        //cy.get('#registerBirthday').type(birthday);
        cy.get('#registerBirthday').click().then(() =>{
            cy.get('.ant-calendar-input').type(birthday + '\n');
        })
        //cy.get('#registerUserMail').click({force: true});
        cy.get('#registerUserMail').scrollIntoView().type(email);
        cy.get('#registerUserName').type('test');
        cy.get('#registerPhoneNumber').scrollIntoView().type(phoneNumber);
        cy.get('#registerPassword').type(password);
        cy.get('#registerPasswordconfirm').type(password);
        //cy.wait(2000);
        cy.get('.ant-tabs-tabpane-active > [style="margin-top: 24px;"] > .ant-form-item-control-wrapper > .ant-form-item-control > .ant-form-item-children > .login-button').click();

        cy.get('#username').type(email);
        cy.get('#password').type(password);
        cy.wait(1000);
        cy.get('.ant-tabs-tabpane-active > [style="margin-top: 24px;"] > .ant-form-item-control-wrapper > .ant-form-item-control > .ant-form-item-children > .login-button').click();

    })


    it('test login and logout module ', function() {
        //cy.reload();
        //cy.get('[aria-selected="false"]').click();//active = .ant-tabs-tab-active

        const loginEmail = '123@qq.com';
        cy.get('#username').type(loginEmail);
        cy.get('#password').type(password);
        cy.wait(1000);
        cy.get('.ant-tabs-tabpane-active > [style="margin-top: 24px;"] > .ant-form-item-control-wrapper > .ant-form-item-control > .ant-form-item-children > .login-button').click();
        cy.get('.user').click();
        cy.get('.ant-dropdown-menu > :nth-child(3)').click();
        cy.scrollTo(0, 500);
    })
})

