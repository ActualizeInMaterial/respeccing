/****************************************************************************
*
*                             dmental links
*    Copyright (c) 28 Feb 2005 AtKaaZ, AtKaaZ at users.sourceforge.net
*    Portions Copyright (c) 1983-2002 Sybase, Inc. All Rights Reserved.
*
*  ========================================================================
*
*    This file contains Original Code and/or Modifications of Original
*    Code as defined in and that are subject to the Sybase Open Watcom
*    Public License version 1.0 (the 'License'). You may not use this file
*    except in compliance with the License. BY USING THIS FILE YOU AGREE TO
*    ALL TERMS AND CONDITIONS OF THE LICENSE. A copy of the License is
*    provided with the Original Code and Modifications, and is also
*    available at www.sybase.com/developer/opensource.
*
*    The Original Code and all software distributed under the License are
*    distributed on an 'AS IS' basis, WITHOUT WARRANTY OF ANY KIND, EITHER
*    EXPRESS OR IMPLIED, AND SYBASE AND ALL CONTRIBUTORS HEREBY DISCLAIM
*    ALL SUCH WARRANTIES, INCLUDING WITHOUT LIMITATION, ANY WARRANTIES OF
*    MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, QUIET ENJOYMENT OR
*    NON-INFRINGEMENT. Please see the License for the specific language
*    governing rights and limitations under the License.
*
*  ========================================================================
*
* Description: just testing stuff 
*
****************************************************************************/


#include <stdlib.h>
#include <stdio.h>
#include <conio.h>
#include <io.h>

#include "petrackr.h"
#include "dmentalx.h"


dmentalix *test2;

int main(){
    init_error_tracker();
    
    test2=new dmentalix;
    ab_ifnot(test2);

    printf("Erase old files?\n");
    printf("drop anykey to YES or ESC to skip...\n");
    basic_element c=(basic_element)(getch());
    if (c!=27) {
        unlinkall(_fnames);//so we kill the files we can use strictADDelemental()
    }
    
    ab_ifnot(test2->init(_fnames));

    if (c==27) goto skipadd;

    printf("Attempting to add eatoms with BE#0..#255 takes 15seconds\n");
    printf("drop anykey to begin or ESC to skip...\n");
    if (getch()==27) goto skipadd;
    
    c=0;
    while ( !( (kbhit())&&(getch()) ) ){
        printf("attempting to add basic_elemnt==char(%d)",c);
        ab_if_error_after_statement(
        atomID bebe=test2->strict_add_atom_type_E(c);//only used with unlink()
        );
        printf(" :has: atomID==%ld\n",bebe);
        if (c++==255) break;
    }//while

skipadd:
    
    atomID prev=_noID_;

    printf("Attempting to add acatoms to each eatom\n");
    printf("drop anykey to begin or ESC to skip...\n");
    if (getch()==27) goto skipacatoms;
    
    c=0;
    while ( !( (kbhit())&&(getch()) ) ){
        printf("attempt2add ACatom to BE==char(%d)",c);
        ab_if_error_after_statement(
        atomID _atomID_typeE=test2->find_atomID_type_E(c);
        );
        ab_if_error_after_statement(
        atomID bebe=test2->strict_add_atom_type_AC_after_prev(_atomID_typeE,_noID_,prev);//only used with unlink()
        );
        prev=bebe;//at this point we have prev bebe->next=_noID_ unless that
        //funxion updates prev->next to US, which will do! and should DO!
        printf(" :acatom has: atomID==%ld\n",bebe);
        if (c++==255) break;
    }//while
    
    printf("Trying to parse chain from the last added acatom\n");
    printf("drop anykey to begin or ESC to skip...\n");
    if (getch()==27) goto skipacatoms;
//`prev' is last atomID which is an acatom type, so we must get this atom's
//prev&next&type fields and parse by prev, going to next atomID, then repeat
//until prev=_noID_
    
    while ( !( (kbhit())&&(getch()) ) ){
        atomtypes _ty;
        atomID next=_noID_;
        atomID now=prev;
        ret_ifnot( test2->get_atomID_s_type_prev_next(now,_ty,prev,next) );
        ret_if( _ty != _AC_atom );//we know we only have acatoms, by now.
        printf("passing thru type AC w/ atomID==%ld\n",now);
        if (prev==_noID_) break;//no more prev items
    }//while


//FIXME::
    atomID typeE;
    printf("Trying to get list of clones to last eatom\n");
    printf("drop anykey to begin or ESC to skip...\n");
    if (getch()==27) goto skipacatoms;

        ab_if_error_after_statement(
        typeE=test2->find_atomID_type_E(255);
        );
//FIXME:

skipacatoms:
    srand(982);
    printf("Trying find, in random order 256 times takes 5seconds w/optimiz\n");
    printf("drop anykey to begin or ESC to skip...\n");
    if (getch()==27) goto skiprnd;
    
    c=0;
    while ( !( (kbhit())&&(getch()) ) ){
        c--;
        basic_element d=(basic_element)(rand());//no warnings
        printf("cnt %d find atomID of eatom with BE#%d",c,d);
        ab_if_error_after_statement(
        atomID elder=test2->find_atomID_type_E(d);
        );
        if (elder==0) printf(" not found!\n");
        else printf(" :atomIDis: %ld\n",elder);
        if (c==0) break;
    }//while2

skiprnd:
    printf("Trying find, in backward order, takes 5secs w/optimiz\n");
    printf("drop anykey to begin or ESC to skip...\n");
    if (getch()==27) goto skipord;

    c=0;
    while ( !( (kbhit())&&(getch()) ) ){
        c--;
        printf("find atomID of a type E atom (eatom) that has basic_elem #%d",c);
        ab_if_error_after_statement(
        atomID elder=test2->find_atomID_type_E(c);
        );
        if (elder==0) printf(" not found!\n");
        else printf(" :atomIDis: %ld\n",elder);
        if (c==0) break;
    }//while3


skipord:
    ab_ifnot(test2->shutdown());
    delete test2;

//last in line
    deinit_error_tracker();
    printf("\nDone...press key\n");
    getch();
    return 0;
}

