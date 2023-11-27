const page = {
    url: {
        getAllQuestions: 'http://localhost:8080/api/questions',
        getQuestionById: 'http://localhost:8080/api/questions/',
        createQuestion: 'http://localhost:8080/api/questions',
        getAllWords: 'http://localhost:8080/api/words',
        updateQuestion: 'http://localhost:8080/api/questions'
    },
    elements: {},
    loadData: {},
    commands: {}
}

page.elements.modalCreate = $('#modalCreate');
page.elements.frmCreate = $('#frmCreate');
page.elements.vietnameseCre = $('#vietnameseCre');
page.elements.englishCre = $('#englishCre');
page.elements.contentCre = $('#contentCre');
page.elements.wordsCre = $('#wordsCre');
page.elements.btnCreate = $('#btnCreate');

page.elements.bodyQuestion = $('#tbQuestion tbody')
page.elements.loading = $('#loading');

page.elements.modalUpdate = $('#modalUpdate');
page.elements.frmUpdate = $('#frmUpdate');
page.elements.vietnameseUp = $('#vietnameseUp');
page.elements.englishUp = $('#englishUp');
page.elements.contentUp = $('#contentUp');
page.elements.wordsUp = $('#wordsUp');
page.elements.btnUpdate = $('#btnUpdate');
page.elements.questionUpdateId = $('#questionUpdateId');


page.elements.toastLive = $('#liveToast')
page.elements.toastBody = $('#toast-body')
page.elements.btnCloseToast = $('#btnCloseToast')

const toastBootstrap = bootstrap.Toast.getOrCreateInstance(page.elements.toastLive)


let questionId;
async function fetchAllQuestion() {
    return await $.ajax({
        url: page.url.getAllQuestions
    })
}

page.loadData.getAllQuestions = async () => {
    const questions = await fetchAllQuestion();

    questions.forEach(item => {
        const str = page.commands.renderQuestion(item)
        page.elements.bodyQuestion.prepend(str);
    });
}

page.commands.getQuestionById = async (customerId) => {
    const response = await fetch(page.url.getQuestionById + customerId);
    const question = await response.json();
    return question
}


let questionIndex = 1;
page.commands.renderQuestion = (obj) => {
    const questionNumber = questionIndex++;
    return `
                <tr id="tr_${obj.id}">
                     <td>${questionNumber}</td>
                    <td>${obj.vietnamese}</td>
                    <td>${obj.english}</td>
                    <td>${obj.words}</td>
                    <td>${obj.content}</td>
                    <td> 
                    <button class="btn btn-secondary edit" data-id="${obj.id}">
                    <i class="far fa-edit"></i>
                    Update
                     </button>
                    </td>
                    <td>
                    <button class="btn btn-danger">
                    <i class="fas fa-ban"></i>
                    Inactive
                </button>
                    </td>
                </tr>
            `
}


page.elements.bodyQuestion.on('click', '.edit', async function () {
    questionId = $(this).closest('tr').attr('id').split('_')[1];
    console.log(questionId);

    const question = await page.commands.getQuestionById(questionId);
    page.elements.vietnameseUp.val(question.vietnamese);
    page.elements.englishUp.val(question.english);
    page.elements.contentUp.val(question.content);

    const selectedWordIds = question.wordIds;

    selectedWordIds.forEach(wordId => {
        const checkbox = page.elements.modalUpdate.find(`input[type="checkbox"][name="selectedWords"][value="${wordId}"]`);
        checkbox.prop('checked', true);
        toggleBoldLabel(checkbox);
    });

    function toggleBoldLabel(checkbox) {
        const label = checkbox.next('label');
        label.toggleClass('bold-label');
    }    // Hiển thị modal update
    page.elements.modalUpdate.modal('show');
});

page.elements.modalUpdate.on('hidden.bs.modal', function () {
    resetModal();
    resetToggleBold();
});

function resetToggleBold() {
    page.elements.modalUpdate.find('label.bold-label').removeClass('bold-label');
}

page.elements.frmCreate.validate({
    onkeyup: function (element) {
        $(element).valid()
    },
    onclick: false,
    onfocusout: false,
    rules: {
        vietnameseCre: {
            required: true
        },
        englishCre: {
            required: true
        }
    },
    messages: {
        vietnameseCre: {
            required: 'Vietnamese is required'
        },
        englishCre: {
            required: 'English is required'
        }
    },
    errorLabelContainer: "#modalCreate .area-error",
    errorPlacement: function (error, element) {
        error.appendTo("#modalCreate .area-error");
    },
    showErrors: function (errorMap, errorList) {
        if (this.numberOfInvalids() > 0) {
            $("#modalCreate .area-error").removeClass("hide").addClass("show");
        } else {
            $("#modalCreate .area-error").removeClass("show").addClass("hide").empty();
            $("#frmCreate input.error").removeClass("error");
        }
        this.defaultShowErrors();
    },
    submitHandler: () => {
        page.commands.createQuestion()
        return false;
    }
})


page.commands.createQuestion = () => {
    const vietnamese = page.elements.vietnameseCre.val();
    const english = page.elements.englishCre.val();
    const content = page.elements.contentCre.val();
    const wordIds = [];

    $('input[id="wordsCre"]:checked').each(function () {
        wordIds.push($(this).val());
    });
    const question = {
        vietnamese,
        english,
        content,
        wordIds
    };

    page.elements.btnCreate.prop("disabled", true);

    page.elements.loading.removeClass('hide')

    $.ajax(
        {
            method: 'POST',
            url: page.url.createQuestion,
            data: JSON.stringify(question),
            contentType: 'application/json'
        }
    )
        .done((data) => {
            const str = page.commands.renderQuestion(data);
            page.elements.bodyQuestion.prepend(str);

            page.elements.modalCreate.modal('hide');
            page.elements.frmCreate[0].reset();
            resetToggleBold()
            page.elements.toastBody.text('Thêm mới thành công');
            toastBootstrap.show();
            setTimeout(() => {
                page.elements.btnCloseToast.click();
            }, 2000);

            page.loadData.getAllQuestions();
        })
        .fail((err) => {
            const responseJSON = err.responseJSON

            if (responseJSON) {
                let str = '<ul>'
                $.each(responseJSON, (k, v) => {
                    if (k.includes('.')) {
                        str += `<li><label for="${k.split('.')[1] + 'Cre'}">${v}</label></li>`
                    } else {
                        str += `<li><label for="${k + 'Cre'}">${v}</label></li>`
                    }

                })

                str += '</ul>'

                $('#modalCreate .area-error').append(str).removeClass('hide').css('display', '')
            }
        })
        .always(() => {
            page.elements.btnCreate.prop("disabled", false);
            page.elements.loading.addClass('hide');
            $('#modalCreate .area-error').empty().addClass('hide');
        });

}

page.commands.updateQuestion = async () => {
    const vietnamese = page.elements.vietnameseUp.val();
    const english = page.elements.englishUp.val();
    const content = page.elements.contentUp.val();
    const wordIds = [];

    $('input[id="wordsUp"]:checked').each(function () {
        wordIds.push($(this).val());
    });

    const question = {
        vietnamese,
        english,
        content,
        wordIds
    }

    page.elements.btnUpdate.prop("disabled", true);
    page.elements.loading.removeClass('hide');

    try {
        const response = await $.ajax({
            method: 'PUT',
            url: page.url.getQuestionById + questionId,
            data: JSON.stringify(question),
            contentType: 'application/json'
        });

        const str = page.commands.renderQuestion(response);
        $("#tr_" + questionId).replaceWith(str);

        page.elements.modalUpdate.modal('hide');

        page.elements.toastBody.text('Edit thành công');
        toastBootstrap.show();

        setTimeout(() => {
            page.elements.btnCloseToast.click();
        }, 2000);
    } catch (err) {
        const responseJSON = err.responseJSON;

        if (responseJSON) {
            let str = '<ul>';
            $.each(responseJSON, (k, v) => {
                if (k.includes('.')) {
                    str += `<li><label for="${k.split('.')[1] + 'Up'}">${v}</label></li>`;
                } else {
                    str += `<li><label for="${k + 'Up'}">${v}</label></li>`;
                }
            });

            str += '</ul>';

            $('#modalUpdate .area-error').append(str).removeClass('hide').css('display', '');
        }
    } finally {
        page.elements.btnUpdate.prop("disabled", false);
        page.elements.loading.addClass('hide');
    }
};

page.elements.btnUpdate.on('click', () => {
    page.commands.updateQuestion();
});

page.elements.btnCreate.on('click', async () => {
    page.elements.frmCreate.trigger('submit')

})
page.elements.btnUpdate.on('click', async () => {
    page.elements.frmUpdate.trigger('submit')
})

function resetModal() {
    page.elements.frmUpdate[0].reset();
    resetToggleBold();
    $('#modalUpdate .area-error').empty().addClass('hide');
}

$(async () => {
    page.loadData.getAllQuestions()
})