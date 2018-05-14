$( document ).ready(function() {
var checkboxes =  $('.userSkillChechkbox');

    checkboxes.each(function() {
        $(this).html( $(this).attr('checked','checked') );
     });
});
// function myFilter() {
//     var filter=$( "#filterId" ).val();
//     var otherSkills =  $('.otherSkill');
//     otherSkills.each(function() {
//         $(this).html( $(this).parent().toggle($(this).text().startsWith(filter)));
//     });
// }