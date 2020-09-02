describe('User Test', () =>{
    beforeEach(()=>{
        cy.visit('/');
        const loginEmail = '123@qq.com';
        const password = "123456";
        cy.get('#username').type(loginEmail);
        cy.get('#password').type(password);
        //cy.wait(2000);
        cy.get('.ant-tabs-tabpane-active > [style="margin-top: 24px;"] > .ant-form-item-control-wrapper > .ant-form-item-control > .ant-form-item-children > .login-button').click();
    });

    it('test user info',function (){
        const userName = "test";
        const phone = "12345678901";
        const password = "123456";
        cy.get('.ant-menu > :nth-child(4)').click();
        cy.wait(1000);
        cy.get('.ant-btn').click();
        cy.get('#coordinated_userName').type(userName);
        cy.get('#coordinated_phoneNumber').type(phone);
        cy.get('#coordinated_password').type(password);
        cy.get('.ant-btn-primary').click();
        cy.scrollTo(0, -500);
    })


})