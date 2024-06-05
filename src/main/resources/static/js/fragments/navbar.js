document.addEventListener('DOMContentLoaded', function() {
    console.log('DOM fully loaded and parsed');
    const menuIcon = document.getElementById('menuIcon');
    const menuNavResponsive = document.getElementById('menuNavResponsive');

    if (menuIcon && menuNavResponsive) {
        console.log('menuIcon and menuNavResponsive found');
        menuIcon.addEventListener('click', function() {
            console.log('menuIcon clicked');
            if (menuNavResponsive.style.maxHeight) {
                console.log('Hiding menuNavResponsive');
                menuNavResponsive.style.maxHeight = null;
            } else {
                console.log('Showing menuNavResponsive');
                menuNavResponsive.style.maxHeight = menuNavResponsive.scrollHeight + 'px';
            }
        });
    } else {
        console.error('menuIcon or menuNavResponsive not found');
    }
});
