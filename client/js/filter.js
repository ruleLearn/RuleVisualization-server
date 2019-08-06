Vue.component('characteristic-row', {
    props: ['name', 'characteristics'],
    template: `<tr>
        <td>{{ name }}</td>
        <td><vue-slider v-model="characteristics[name].range"></td>
    </tr>`
});