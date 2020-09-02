describe('Reserve Test',() => {
    beforeEach(()=>{
        cy.visit('/');
        cy.reload();
    });

    it('test reserve hotel and show order module', function () {

        const loginEmail = '123@qq.com';
        const password = "123456";
        cy.get('#username').type(loginEmail);
        cy.get('#password').type(password);
        //cy.wait(2000);
        cy.get('.ant-tabs-tabpane-active > [style="margin-top: 24px;"] > .ant-form-item-control-wrapper > .ant-form-item-control > .ant-form-item-children > .login-button').click();

        cy.scrollTo(0, 500);
        cy.get(':nth-child(1) > .ant-card-cover > img').then((hotel)=>{
            cy.get(hotel).scrollIntoView();
            cy.wait(100);
            cy.get(hotel).click();
        })
        cy.wait(1000);
        cy.get('[data-row-key="0"] > :nth-child(6) > :nth-child(1) > .ant-btn').click();

        //add order
        const name = "test";
        const phone = "12345678901";
        const leaveDate = "2020-07-04";
        cy.get('#orderModal_clientName').type(name);
        cy.get('#orderModal_phoneNumber').type(phone);
        cy.get('#orderModal_date').click().then(()=>{
            cy.get('.ant-calendar-today > .ant-calendar-date').click();
            cy.get('.ant-calendar-range-right > .ant-calendar-input-wrap > .ant-calendar-date-input-wrap > .ant-calendar-input').type(leaveDate + '\n' );
        });
        cy.get('#orderModal_peopleNum').click().then((p)=>{
            cy.get(p).type('\n');
        });
        cy.get('#orderModal_haveChild').click();
        cy.get('#orderModal_roomNum').click().type('\n');
        cy.get('.ant-checkbox-group > .ant-table-wrapper > .ant-spin-nested-loading > .ant-spin-container > .ant-table > .ant-table-content ').scrollIntoView().click();
        cy.get('div > .ant-btn-primary').click();

        //show order
        cy.get('.ant-menu > :nth-child(4)').click();
        cy.wait(1000);
        cy.get('.ant-tabs-nav > :nth-child(1) > :nth-child(2)').click();
        cy.get('[data-row-key="0"] > :nth-child(10) > span[data-v-5d917ec8=""] > .ant-btn-primary').click();
        cy.wait(1000);
        cy.get('.ant-modal-close-x').click();
    })

    it('test check in and check out module', () =>{
        const loginEmail = '3333@qq.com';
        const password = "123456";
        cy.get('#username').type(loginEmail);
        cy.get('#password').type(password);
        cy.wait(1000);
        //cy.scrollTo(0, -500);
        cy.get('.ant-tabs-tabpane-active > [style="margin-top: 24px;"] > .ant-form-item-control-wrapper > .ant-form-item-control > .ant-form-item-children > .login-button').click();

        cy.scrollTo(0, -500);
        cy.get('.ant-menu > :nth-child(4)').click();
        cy.get('[aria-selected="false"]').click();
        cy.get('[data-row-key="0"] > :nth-child(10) > span[data-v-19d0799a=""] > .ant-btn-primary').click();
        cy.get('.button-info > :nth-child(1)').click();
        cy.get('.button-info > :nth-child(2)').click();
        cy.scrollTo(0, -500);
        cy.get('.ant-modal-close-x').click();
        cy.scrollTo(0, -500);
        cy.wait(1000);
    })

    it('test cancel order module', () =>{
        const loginEmail = '123@qq.com';
        const password = "123456";
        cy.get('#username').type(loginEmail);
        cy.get('#password').type(password);
        cy.wait(1000);
        cy.scrollTo(0, -500);
        cy.get('.ant-tabs-tabpane-active > [style="margin-top: 24px;"] > .ant-form-item-control-wrapper > .ant-form-item-control > .ant-form-item-children > .login-button').click();

        cy.scrollTo(0, 500);
        cy.get(':nth-child(1) > .ant-card-cover > img').then((hotel)=>{
            cy.get(hotel).scrollIntoView();
            cy.wait(100);
            cy.get(hotel).click();
        })
        cy.wait(1000);
        cy.get('[data-row-key="0"] > :nth-child(6) > :nth-child(1) > .ant-btn').click();

        //add order
        const name = "test";
        const phone = "12345678901";
        const leaveDate = "2020-07-04";
        cy.get('#orderModal_clientName').type(name);
        cy.get('#orderModal_phoneNumber').type(phone);
        cy.get('#orderModal_date').click().then(()=>{
            cy.get('.ant-calendar-today > .ant-calendar-date').click();
            cy.get('.ant-calendar-range-right > .ant-calendar-input-wrap > .ant-calendar-date-input-wrap > .ant-calendar-input').type(leaveDate + '\n' );
        });
        cy.get('#orderModal_peopleNum').click().then((p)=>{
            cy.get(p).type('\n');
        });
        cy.get('#orderModal_haveChild').click();
        cy.get('#orderModal_roomNum').click().type('\n');
        cy.get('.ant-checkbox-group > .ant-table-wrapper > .ant-spin-nested-loading > .ant-spin-container > .ant-table > .ant-table-content ').scrollIntoView().click();
        cy.get('div > .ant-btn-primary').click();
        cy.reload();
        cy.get('.ant-menu > :nth-child(4)').click();
        cy.wait(1000);
        cy.get('.ant-tabs-nav > :nth-child(1) > :nth-child(2)').click();
        cy.get('[data-row-key="1"] > :nth-child(10) > span[data-v-5d917ec8=""] > .ant-btn-danger').click();
        cy.get('.ant-popover-buttons > .ant-btn-primary').click();
    })

})