# PMD

Установка git: https://git-scm.com/book/ru/v2/Введение-Установка-Git

Работа с Git/GitHub:

1. Нажимаем на кнопку Code и копируем ссылку оттуда.
2. Открываем командную строку и переходим по пути (**cd путь_к_файлу**), куда желаем склонировать репозиторий.
3. Пишем в командной строке **git clone скопированная_ссылка** - создается локальный Git-репозиторий.
4. Переходим в репозиторий через cd.
5. Пишем **git checkout имя_своей_ветки** и экспериментируем!
    
    **git branch** - посмотреть, какие есть ветки.

6. Наэкспериментировались - пишем в консоли **git add .** (внимание! точка, типа все файлы). Ваша версия добавляется в неподтвержденные коммиты
7. **git commit -m "что-нибудь пишете по приколу"**. Коммит подтверждается.

8. **git push** - версия отправляется на GitHub.