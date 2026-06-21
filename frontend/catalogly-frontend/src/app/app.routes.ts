import { Routes } from '@angular/router';
import { LandingLayout } from './components/landing-page/layout/landing-layout/landing-layout';
import { LandingHome } from './components/landing-page/home/landing-home/landing-home';
import { SellerRegister } from './components/seller-side/auth/seller-register/seller-register';
import { SellerLogin } from './components/seller-side/auth/seller-login/seller-login';
import { SellerLayout } from './components/seller-side/catalog/layout/seller-layout/seller-layout';
import { SellerDashboard } from './components/seller-side/catalog/seller-dashboard/seller-dashboard';
import { SellerShowItems } from './components/seller-side/catalog/item/seller-show-items/seller-show-items';
import { SellerCreateCatalog } from './components/seller-side/catalog/seller-create-catalog/seller-create-catalog';
import { SellerCreateItem } from './components/seller-side/catalog/item/seller-create-item/seller-create-item';
import { SellerShowCategorys } from './components/seller-side/catalog/category/seller-show-categorys/seller-show-categorys';


export const routes: Routes = [
    {
        path: '',
        component:LandingLayout,
        children: [
            {
                path:'',
                component: LandingHome
            },
            {
                path:'seller/register',
                component: SellerRegister
            },
            {
                path:'seller/login',
                component: SellerLogin
            },
        ]
    },
    {
        path: 'catalog',
        component: SellerLayout,
        children: [
            {
                path: 'create',
                component:SellerCreateCatalog
            },
            {
                path: 'dashboard',
                component: SellerDashboard
            },
            {
                path: 'products',
                component:SellerShowItems,
            },
            {
                path:"products/create",
                component:SellerCreateItem
            },
            {
                path: 'categories',
                component:SellerShowCategorys
            },
            
            
        ]
    }
    
];
