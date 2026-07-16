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
import { SellerCreateCategory } from './components/seller-side/catalog/category/seller-create-category/seller-create-category';
import { SellerShowItemsOfCategory } from './components/seller-side/catalog/category/seller-show-items-of-category/seller-show-items-of-category';
import { SellerShowCoupons } from './components/seller-side/catalog/coupon/seller-show-coupons/seller-show-coupons';
import { SellerCreateCoupon } from './components/seller-side/catalog/coupon/seller-create-coupon/seller-create-coupon';
import { SellerShowSuppliers } from './components/seller-side/catalog/supplier/seller-show-suppliers/seller-show-suppliers';
import { SellerCreateSupplier } from './components/seller-side/catalog/supplier/seller-create-supplier/seller-create-supplier';
import { SellerShowItem } from './components/seller-side/catalog/item/seller-show-item/seller-show-item';
import { SellerEditCategory } from './components/seller-side/catalog/category/seller-edit-category/seller-edit-category';
import { SellerShowSupplierItems } from './components/seller-side/catalog/supplier/seller-show-supplier-items/seller-show-supplier-items';
import { SellerShowSupplierStockOrders } from './components/seller-side/catalog/supplier/seller-show-supplier-stock-orders/seller-show-supplier-stock-orders';
import { SellerCreateStockOrder } from './components/seller-side/catalog/stock-order/seller-create-stock-order/seller-create-stock-order';
import { SellerShowStockOrder } from './components/seller-side/catalog/stock-order/seller-show-stock-order/seller-show-stock-order';
import { SellerTieItemWithCprod } from './components/seller-side/catalog/stock-order/seller-tie-item-with-cprod/seller-tie-item-with-cprod';
import { SellerShowOrders } from './components/seller-side/catalog/order/seller-show-orders/seller-show-orders';
import { UserLayout } from './components/user-side/catalog/layout/user-layout/user-layout';
import { UserShowCatalog } from './components/user-side/catalog/user-show-catalog/user-show-catalog';
import { UserShowCart } from './components/user-side/catalog/cart/user-show-cart/user-show-cart';
import { UserLogin } from './components/user-side/auth/user-login/user-login';
import { UserRegister } from './components/user-side/auth/user-register/user-register';


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
                path:"products/:id",
                component:SellerShowItem
            },
            {
                path: 'categories',
                component:SellerShowCategorys
            },
            {
                path: 'categories/create',
                component:SellerCreateCategory
            },
            {
                path: 'categories/:id/products',
                component:SellerShowItemsOfCategory
            },
            {
                path: 'categories/:id/edit',
                component:SellerEditCategory
            },
            {
                path: 'coupons',
                component:SellerShowCoupons
            },
            {
                path: 'coupons/create',
                component:SellerCreateCoupon
            },
            {
                path: 'suppliers',
                component:SellerShowSuppliers
            },
            {
                path: 'suppliers/create',
                component:SellerCreateSupplier
            },
            {
                path: 'suppliers/:id/items',
                component:SellerShowSupplierItems
            },
            {
                path: 'suppliers/:id/stock-orders',
                component:SellerShowSupplierStockOrders
            },
            {
                path: 'suppliers/:id/stock-order/create',
                component:SellerCreateStockOrder
            },
            {
                path: 'stock-order/:id',
                component:SellerShowStockOrder
            },
            {
                path: 'stock-order/:id/tie-items',
                component:SellerTieItemWithCprod
            },
            {
                path: 'orders',
                component:SellerShowOrders
            },
            
        ]
    },
    {
        path: 'catalogly',
        component: UserLayout,
        children:[
            {
                path:':slug',
                component:UserShowCatalog
            },
            {
                path:':slug/cart',
                component:UserShowCart
            },
            {
                path:'user/login',
                component:UserLogin
            },
            {
                path:'user/register',
                component:UserRegister
            }

        ]
    }
    
];
