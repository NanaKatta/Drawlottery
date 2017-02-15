package com.hudongwx.drawlottery.mobile.conf.cache;

import com.jhlabs.image.PinchFilter;
import com.jhlabs.math.ImageFunction2D;
import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.UniColorBackgroundGenerator;
import com.octo.captcha.component.image.color.RandomListColorGenerator;
import com.octo.captcha.component.image.deformation.ImageDeformation;
import com.octo.captcha.component.image.deformation.ImageDeformationByBufferedImageOp;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.textpaster.GlyphsPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;
import com.octo.captcha.component.image.textpaster.glyphsvisitor.GlyphsVisitors;
import com.octo.captcha.component.image.textpaster.glyphsvisitor.OverlapGlyphsUsingShapeVisitor;
import com.octo.captcha.component.image.textpaster.glyphsvisitor.TranslateAllToRandomPointVisitor;
import com.octo.captcha.component.image.textpaster.glyphsvisitor.TranslateGlyphsVerticalRandomVisitor;
import com.octo.captcha.component.image.wordtoimage.DeformedComposedWordToImage;
import com.octo.captcha.component.image.wordtoimage.WordToImage;
import com.octo.captcha.component.word.wordgenerator.RandomWordGenerator;
import com.octo.captcha.component.word.wordgenerator.WordGenerator;
import com.octo.captcha.engine.CaptchaEngine;
import com.octo.captcha.engine.image.ListImageCaptchaEngine;
import com.octo.captcha.image.gimpy.GimpyFactory;
import com.octo.captcha.service.captchastore.FastHashMapCaptchaStore;
import com.octo.captcha.service.image.DefaultManageableImageCaptchaService;
import com.octo.captcha.service.image.ImageCaptchaService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.awt.*;
import java.util.ArrayList;

/**
 * 开发公司：hudongwx.com<br/>
 * 版权：294786949@qq.com<br/>
 * <p>
 *
 * @author origin
 * @version 1.0, 2017/1/21 0021 <br/>
 * @desc <p>
 * <p>
 * 创建　origin　2017/1/21 0021　<br/>
 * <p>
 * 验证码缓存配置
 * <p>
 * @email 294786949@qq.com
 */
@Configuration
public class CaptchaConfig {

    /**
     * 图片验证码配置
     */
    @Bean
    public ImageCaptchaService getImageCaptchaService(CaptchaEngine engine){
        return new DefaultManageableImageCaptchaService(new FastHashMapCaptchaStore(),engine,180,50000,35000);
    }
    @Bean
    public CaptchaEngine getEngine(){
        return new ListImageCaptchaEngine() {
            @Override
            protected void buildInitialFactories() {

                //word generator,去除1,i,l,0,o等混淆字符
                WordGenerator dictionnaryWords = new RandomWordGenerator("23456789abcdefghjkmnpqrstuvwxyz");
                //wordtoimage components
                TextPaster randomPaster = new GlyphsPaster(4, 4,
                        new RandomListColorGenerator(
                                new Color[]{
                                        new Color(23, 170, 27),
                                        new Color(220, 34, 11),
                                        new Color(23, 67, 172)})
                        ,new GlyphsVisitors[]{
                        new TranslateGlyphsVerticalRandomVisitor(1),
                        // new RotateGlyphsRandomVisitor(Math.PI/32),
                        new OverlapGlyphsUsingShapeVisitor(1),
                        new TranslateAllToRandomPointVisitor()
                });
                BackgroundGenerator back = new UniColorBackgroundGenerator(
                        150, 60, Color.white);

                FontGenerator shearedFont = new RandomFontGenerator(50,
                        50,
                        new Font[]{
                                new Font("nyala",Font.BOLD, 50)
                                ,
                                new Font("Bell MT",  Font.BOLD, 50)
                                ,
                                new Font("Credit valley",  Font.BOLD, 50)
                        }
                        ,false);


                PinchFilter pinch = new PinchFilter();

                pinch.setAmount(-.5f);
                pinch.setRadius(70);
                pinch.setAngle((float) (Math.PI/16));
                pinch.setCentreX(0.5f);
                pinch.setCentreY(-0.01f);
                pinch.setEdgeAction(ImageFunction2D.CLAMP);

                PinchFilter pinch2 = new PinchFilter();
                pinch2.setAmount(-.6f);
                pinch2.setRadius(70);
                pinch2.setAngle((float) (Math.PI/16));
                pinch2.setCentreX(0.3f);
                pinch2.setCentreY(1.01f);
                pinch2.setEdgeAction(ImageFunction2D.CLAMP);

                PinchFilter pinch3 = new PinchFilter();
                pinch3.setAmount(-.6f);
                pinch3.setRadius(70);
                pinch3.setAngle((float) (Math.PI/16));
                pinch3.setCentreX(0.8f);
                pinch3.setCentreY(-0.01f);
                pinch3.setEdgeAction(ImageFunction2D.CLAMP);



                java.util.List<ImageDeformation> textDef =  new ArrayList<ImageDeformation>();
                textDef.add(new ImageDeformationByBufferedImageOp(pinch));
                textDef.add(new ImageDeformationByBufferedImageOp(pinch2));
                textDef.add(new ImageDeformationByBufferedImageOp(pinch3));

                //word2image 1
                WordToImage word2image;
                word2image = new DeformedComposedWordToImage(false,shearedFont, back, randomPaster,
                        new ArrayList<>(),
                        new ArrayList<>(),
                        textDef


                );


                this.addFactory(
                        new GimpyFactory(dictionnaryWords,
                                word2image, false));

            }
        };
    }

}
