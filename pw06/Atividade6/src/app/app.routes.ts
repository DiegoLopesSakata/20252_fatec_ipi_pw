import { Routes } from '@angular/router';
import { Sobre } from './sobre/sobre';
import { Lista } from './lista/lista';

export const routes: Routes = [
    {path:"sobre", component:Sobre},
    {path:"", component:Sobre},
    {path:"lista", component:Lista}
];
