let userID = $('#userID')

page.elements.btnCustomer.on('click', function () {
    page.commands.renderCustomer();
})
page.commands.renderCustomer = () => {
    $.ajax({
        url: page.url.getCustomerById + customerID
    })
        .done(async (data) => {
            page.elements.fullNameUser.val(data.fullName);
            page.elements.emailUser.val(data.email);
            page.elements.nameUser.val(data.username);
            page.elements.phoneUser.val(data.phoneNumber);
        })
    page.elements.modalCustomer.modal('show');
}
