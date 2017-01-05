cordova.define("ru.slowkazak.toucancards.toucancards", function (require, exports, module) {
    var exec = require('cordova/exec');

    exports.start = function (act, arg, success, error) {
        // Если объект с аргументами не пуст
        if (Object.keys(arg).length) {
            var accessible_json_params = [
                {
                    act: "payment",
                    params: [
                        "PackageName",
                        "SecureCode",
                        "Amount",
                        "Description",
                        "Email",
                        "Phone",
                        "ValueAddedTaxRate"
                    ]
                }
            ]
            accessible_json_params = accessible_json_params.filter(item => item.act === act).pop();
            console.log(accessible_json_params.params);

            var action = "ru.toucan.PAYMENT";
//проверяем на наличие лишних свойств, если находим - удаляем
            for (var itm in arg) {
                console.log(itm)
                if (accessible_json_params.params.indexOf(itm)<0) {
                    delete arg[itm];
                }
            }

            exec(success, error, "toucancards", act, [arg]);
        }
    };

});

/*
 https://github.com/smart-fin/AndroidApi/tree/master/V3
 Amount сумма платежа в МДЕ (в копейках) - целочисленный формат
 Description назначение платежа
 FullDescription подробное назначение платежа, используется для печати подробной информации об услуге на кассовом чеке (поддержка ФР Штрих-м)
 ValueAddedTaxRate НДС (в промилях)
 Email E-mail для отправки терминального чека
 Phone Номер телефона для отправки терминального чека
 GetPayInfo переключатель показывающий надо ли возвращать полную информацию о платеже (1-да, 2-нет)
 FiscalizationFlag Необходимость фискализации (0 - фискализация нужна(значение по-умолчанию), 1-фискализация не нужна)
 */
