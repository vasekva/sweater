<#import "layouts/default.ftlh" as layout>
<#import "parts/login.ftlh" as l>

<@layout.page currentPage="none">
    <h2>Вход в аккаунт</h2>
    <@l.login "/login" false/>
</@layout.page>