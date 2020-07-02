<!-- Шаблон страницы приветсвия -->
<#import "layouts/default.ftlh" as layout> <!-- Подключение базового шаблона -->

<@layout.page currentPage="greeting">
    <h5>Hello, guest</h5>
    <div>This is a simple clone off Twitter</div>
</@layout.page>
