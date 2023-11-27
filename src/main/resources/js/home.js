let userID = $('#userID').val();
const btnUser = $('#btnUser');
btnUser.on('click',async function () {
    await renderCustomer();
})
async  function renderCustomer()  {
    await $.ajax({
        url: "http://localhost:8080/quiz/",
        method: "GET"
    })
        .done( (data) => {
            console.log(data)
            $('#fullName').val(data.fullName);
            $('#email').val(data.email);
            $('#phoneNumber').val(data.phoneNumber);
        })
    $('#modalCustomer').modal('show');
}
