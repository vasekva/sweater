<#import "layouts/default.ftlh" as layout> <!-- Подключение базового шаблона -->
<#import "parts/login.ftlh" as l> <!-- Подключение шаблона формы логина -->

<@layout.page currentPage="none">
    <div class="mb-1">Add new user</div>
    ${message?ifExists}
    <@l.login "/registration" true/>
</@layout.page>